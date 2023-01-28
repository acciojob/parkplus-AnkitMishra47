package com.driver.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;
    int numberOfHours ;

    User user ;

    Spot spot;

    Payment payment;


    public Reservation(int numberOfHours, User user, Spot spot, Payment payment) {
        this.numberOfHours = numberOfHours;
        this.user = user;
        this.spot = spot;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Reservation(int numberOfHours, User user, Spot spot) {
        this.numberOfHours = numberOfHours;
        this.user = user;
        this.spot = spot;
    }

    public int getTotalAmount(){
        return getSpot().getPricePerHour() * getNumberOfHours();
    }
}
