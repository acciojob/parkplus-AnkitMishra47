package com.driver.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    SpotType spotType;

    int pricePerHour;

    boolean occupied;

    ParkingLot parkingLot;

    List<Reservation> reservationList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Spot() {
    }

    public Spot(int pricePerHour, int numberOfWheels, ParkingLot parkingLot) {
        this.spotType = getSpotType(numberOfWheels);
        this.pricePerHour = pricePerHour;
        this.parkingLot = parkingLot;
    }

    public static SpotType getSpotType(int numberOfWheels) {

        if (numberOfWheels == 2){
            return SpotType.TWO_WHEELER;
        }
        else if (numberOfWheels == 4){
            return SpotType.FOUR_WHEELER;
        }

        return SpotType.OTHERS;
    }
}
