// CarRentalManager JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Load cars on page load
    loadCars();
    
    // Initialize date pickers
    initializeDatePickers();
    
    // Load admin panel if on admin page
    if (document.getElementById('admin-panel')) {
        loadAdminCars();
    }
});

function loadCars(categoryId = null, maxPrice = null) {
    const url = `/api/cars${categoryId || maxPrice ? '?' : ''}${categoryId ? `categoryId=${categoryId}` : ''}${maxPrice ? (categoryId ? '&' : '') + `maxPrice=${maxPrice}` : ''}`;
    
    fetch(url)
        .then(response => response.json())
        .then(cars => {
            displayCars(cars);
        })
        .catch(error => {
            console.error('Error loading cars:', error);
        });
}

function displayCars(cars) {
    const carsContainer = document.getElementById('cars-container');
    if (!carsContainer) return;
    
    carsContainer.innerHTML = '';
    
    if (cars.length === 0) {
        carsContainer.innerHTML = '<div class="col-12"><div class="alert alert-info text-center">No cars available</div></div>';
        return;
    }
    
    cars.forEach(car => {
        const carCard = document.createElement('div');
        carCard.className = 'col-md-6 col-lg-4 fade-in';
        carCard.innerHTML = `
            <div class="card car-card h-100">
                <img src="${car.photoUrl}" class="card-img-top" alt="${car.brand} ${car.model}">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title car-title">${car.brand} ${car.model}</h5>
                    <p class="card-text car-details">
                        Year: ${car.year} | ${car.category ? car.category.name : 'N/A'}<br>
                        License: ${car.licensePlate}
                    </p>
                    <p class="card-text">${car.description}</p>
                    <div class="mt-auto">
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="car-price">$${car.pricePerDay}/day</span>
                            <button class="btn btn-primary btn-sm" onclick="openBookingModal(${car.id}, '${car.brand} ${car.model}', ${car.pricePerDay})">
                                Book Now
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        carsContainer.appendChild(carCard);
    });
}

function initializeDatePickers() {
    // Add date validation to booking form
    const startDateInput = document.getElementById('booking-start-date');
    const endDateInput = document.getElementById('booking-end-date');
    
    if (startDateInput) {
        startDateInput.min = new Date().toISOString().split('T')[0];
        
        startDateInput.addEventListener('change', function() {
            endDateInput.min = this.value;
        });
    }
}

function openBookingModal(carId, carName, pricePerDay) {
    document.getElementById('booking-car-id').value = carId;
    document.getElementById('booking-car-name').textContent = carName;
    document.getElementById('booking-price-per-day').textContent = pricePerDay;
    
    // Calculate price when dates change
    document.getElementById('booking-start-date').addEventListener('change', calculateTotalPrice);
    document.getElementById('booking-end-date').addEventListener('change', calculateTotalPrice);
    
    const modal = new bootstrap.Modal(document.getElementById('booking-modal'));
    modal.show();
}

function calculateTotalPrice() {
    const startDate = new Date(document.getElementById('booking-start-date').value);
    const endDate = new Date(document.getElementById('booking-end-date').value);
    const pricePerDay = parseFloat(document.getElementById('booking-price-per-day').textContent);
    
    if (startDate && endDate && !isNaN(startDate) && isNaN(endDate)) {
        const timeDiff = endDate.getTime() - startDate.getTime();
        const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1; // +1 to include both start and end dates
        const totalPrice = daysDiff * pricePerDay;
        
        document.getElementById('booking-total-price').textContent = totalPrice.toFixed(2);
        document.getElementById('booking-total-price-display').textContent = `$${totalPrice.toFixed(2)}`;
    }
}

function bookCar() {
    const carId = document.getElementById('booking-car-id').value;
    const startDate = document.getElementById('booking-start-date').value;
    const endDate = document.getElementById('booking-end-date').value;
    const deposit = parseFloat(document.getElementById('booking-deposit').value) || 0;
    
    if (!startDate || !endDate) {
        alert('Please select both start and end dates');
        return;
    }
    
    const rentalData = {
        carId: parseInt(carId),
        startDate: startDate,
        endDate: endDate,
        deposit: deposit
    };
    
    fetch('/api/rentals', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(rentalData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.id) {
            alert('Car booked successfully!');
            const modal = bootstrap.Modal.getInstance(document.getElementById('booking-modal'));
            modal.hide();
            // Reload cars to reflect updated availability
            loadCars();
        } else {
            alert('Error booking car: ' + (data.error || 'Unknown error'));
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error booking car: ' + error.message);
    });
}

function loadAdminCars() {
    fetch('/api/admin/cars')
        .then(response => response.json())
        .then(cars => {
            displayAdminCars(cars);
        })
        .catch(error => {
            console.error('Error loading admin cars:', error);
        });
}

function displayAdminCars(cars) {
    const carsTable = document.getElementById('admin-cars-table');
    if (!carsTable) return;
    
    const tbody = carsTable.querySelector('tbody');
    tbody.innerHTML = '';
    
    cars.forEach(car => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${car.id}</td>
            <td>${car.brand}</td>
            <td>${car.model}</td>
            <td>${car.year}</td>
            <td>${car.licensePlate}</td>
            <td>$${car.pricePerDay}/day</td>
            <td><span class="status-${car.status.toLowerCase()}">${car.status}</span></td>
            <td>
                <button class="btn btn-sm btn-primary me-1" onclick="editCar(${car.id})">Edit</button>
                <button class="btn btn-sm btn-danger" onclick="deleteCar(${car.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function addCar() {
    const brand = document.getElementById('add-car-brand').value;
    const model = document.getElementById('add-car-model').value;
    const year = parseInt(document.getElementById('add-car-year').value);
    const licensePlate = document.getElementById('add-car-license').value;
    const pricePerDay = parseFloat(document.getElementById('add-car-price').value);
    const photoUrl = document.getElementById('add-car-photo').value;
    const description = document.getElementById('add-car-description').value;
    const status = document.getElementById('add-car-status').value;
    
    const carData = {
        brand: brand,
        model: model,
        year: year,
        licensePlate: licensePlate,
        pricePerDay: pricePerDay,
        photoUrl: photoUrl,
        description: description,
        status: status
    };
    
    fetch('/api/admin/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(carData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.id) {
            alert('Car added successfully!');
            loadAdminCars();
            // Reset form
            document.getElementById('add-car-form').reset();
            // Hide modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('add-car-modal'));
            modal.hide();
        } else {
            alert('Error adding car: ' + (data.error || 'Unknown error'));
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error adding car: ' + error.message);
    });
}

function editCar(carId) {
    // This would typically open an edit modal with the car data
    alert('Edit functionality would be implemented here for car ID: ' + carId);
}

function deleteCar(carId) {
    if (!confirm('Are you sure you want to delete this car?')) {
        return;
    }
    
    fetch(`/api/admin/cars/${carId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('Car deleted successfully!');
            loadAdminCars();
        } else {
            alert('Error deleting car');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error deleting car: ' + error.message);
    });
}

// Filter cars by category and price
function filterCars() {
    const categoryId = document.getElementById('category-filter')?.value || null;
    const maxPrice = document.getElementById('price-filter')?.value || null;
    loadCars(categoryId, maxPrice);
}

// Form submission handlers
document.addEventListener('submit', function(e) {
    if (e.target.id === 'login-form') {
        e.preventDefault();
        
        const formData = new FormData(e.target);
        const loginData = {
            username: formData.get('username'),
            password: formData.get('password')
        };
        
        fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                window.location.href = '/index';
            } else {
                alert(data.error || 'Login failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Login error: ' + error.message);
        });
    }
    
    if (e.target.id === 'register-form') {
        e.preventDefault();
        
        const formData = new FormData(e.target);
        const registerData = {
            username: formData.get('username'),
            password: formData.get('password'),
            email: formData.get('email'),
            phone: formData.get('phone'),
            driverLicense: formData.get('driverLicense')
        };
        
        fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                alert('Registration successful! You can now login.');
                window.location.href = '/login';
            } else {
                alert(data.error || 'Registration failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Registration error: ' + error.message);
        });
    }
});