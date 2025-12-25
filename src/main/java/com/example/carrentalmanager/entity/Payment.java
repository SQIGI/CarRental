package com.example.carrentalmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
    
    private Double amount;
    private LocalDate paymentDate;
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    // Constructors
    public Payment() {}

    public Payment(Rental rental, Double amount, LocalDate paymentDate, PaymentMethod method) {
        this.rental = rental;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Rental getRental() { return rental; }
    public void setRental(Rental rental) { this.rental = rental; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
}