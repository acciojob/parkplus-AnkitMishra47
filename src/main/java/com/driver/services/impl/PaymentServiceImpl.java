package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = getReservation(reservationId);


        int totalAmount = reservation.getTotalAmount();

        if (totalAmount > amountSent){
            throw new Exception("Insufficient Amount");
        }

        if (!modeExists(mode)){
            throw new Exception("Payment mode not detected");
        }

        Payment payment = new Payment(true , getPaymentMode(mode) , reservation);
        reservation.setPayment(payment);

        reservationRepository2.save(reservation);
        paymentRepository2.save(payment);
    }

    private PaymentMode getPaymentMode(String mode) {
        mode = mode.toUpperCase();

        if (mode == "CASH"){
            return PaymentMode.CASH;
        }
        if (mode == "CARD"){
            return PaymentMode.CARD;
        }
        return PaymentMode.UPI;
    }

    private boolean modeExists(String mode) {
        mode = mode.toUpperCase();

        return mode.equals(PaymentMode.CARD) ||
                mode.equals(PaymentMode.UPI) ||
                mode.equals(PaymentMode.CASH);
    }

    public Reservation getReservation(int reservationId){
        return reservationRepository2.findById(reservationId).get();
    }
}
