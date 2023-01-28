package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        ParkingLot  parkingLot  = getParkingLot(parkingLotId);
        List<Spot>  spotList    = parkingLot.getSpotList();

        SpotType spotType = Spot.getSpotType(numberOfWheels);
        Spot spot = null;

        if (spotType == SpotType.TWO_WHEELER)
        {
            /**
             * When spotType is TwoWheeler than all spot are going to work;
             */
             spot = findSpot(spotList , timeInHours);

        }
        else if (spotType == SpotType.FOUR_WHEELER)
        {
            spotList = spotList.stream().filter(val -> val.getSpotType() != SpotType.TWO_WHEELER).collect(Collectors.toList());
            spot = findSpot(spotList , timeInHours);
        }
        else
        {
            spotList = spotList.stream().filter(val -> val.getSpotType() == SpotType.OTHERS).collect(Collectors.toList());
            spot = findSpot(spotList , timeInHours);
        }

        User user = getUser(userId);

        if (spot == null || user == null){
            throw new NullPointerException("Cannot make reservation");
        }

        Reservation reservation = new Reservation(timeInHours, user, spot);

        List<Reservation> reservationList = spot.getReservationList();
        reservationList.add(reservation);
        spot.setReservationList(reservationList);

        reservationList = user.getReservationList();
        reservationList.add(reservation);
        user.setReservationList(reservationList);

        reservationRepository3.save(reservation);

        return reservation;
    }
    public ParkingLot getParkingLot(int parkingId){
        return parkingLotRepository3.findById(parkingId).get();
    }

    public User getUser(int userId){
        return userRepository3.findById(userId).get();
    }

    public Spot findSpot(List<Spot> spotList , Integer timeInHours){
        boolean     spotFound   = false;
        Integer maxPrice = Integer.MAX_VALUE;
        Spot newSpot = new Spot();

        for (Spot spot : spotList){
            int hrs = spot.getPricePerHour();
            int noOfhrs = timeInHours;

            if (hrs*noOfhrs < maxPrice){
                maxPrice = hrs * noOfhrs;
                newSpot = spot;
                spotFound = true;
            }
        }

        return spotFound ? newSpot : null;
    }
}
