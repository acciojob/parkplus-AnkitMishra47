package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name , address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot  parkingLot  = getParkingLot(parkingLotId);
        List<Spot>  spotList    = parkingLot.getSpotList();
        Spot        spot        = new Spot(pricePerHour , numberOfWheels ,parkingLot);

        spotList.add(spot);
        parkingLot.setSpotList(spotList);

        parkingLotRepository1.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        ParkingLot  parkingLot  = getParkingLot(parkingLotId);
        List<Spot>  spotList    = parkingLot.getSpotList();
        Spot        spot        = spotList.stream().filter(val -> val.getId() == spotId).findFirst().get();

        spotList.remove(spot);

        spot.setParkingLot(parkingLot);
        spot.setPricePerHour(pricePerHour);

        spotRepository1.save(spot);

        spotList.add(spot);
        parkingLot.setSpotList(spotList);

        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }

    public ParkingLot getParkingLot(int parkingId){
        return parkingLotRepository1.findById(parkingId).get();
    }

    public Spot getSpot(int spotId){
        return spotRepository1.findById(spotId).get();
    }
}
