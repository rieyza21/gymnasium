package com.project.gymnasium.service;


import com.project.gymnasium.model.Booking;
import com.project.gymnasium.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(String id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setBookingTimeStart(bookingDetails.getBookingTimeStart());
        booking.setDuration(bookingDetails.getDuration());
        booking.setPurpose(bookingDetails.getPurpose());
        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }
}
