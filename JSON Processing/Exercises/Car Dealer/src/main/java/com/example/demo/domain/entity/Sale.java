package com.example.demo.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private int discount;
    private Customer customer;
    private Car car;

    public Sale() {
    }

    @Column(name = "discount")
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToOne
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
