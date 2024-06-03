package com.project.gymnasium.controller;

import com.project.gymnasium.model.Booking;
import com.project.gymnasium.model.BookingStatus;
import com.project.gymnasium.model.BookingType;
import com.project.gymnasium.model.User;
import com.project.gymnasium.service.BookingService;
import io.hypersistence.utils.hibernate.type.range.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);

    }

    @GetMapping("/get")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/get/{id}")
    public Optional<Booking> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/update/{id}")
    public Booking updateBooking(@PathVariable String id, @RequestBody Booking bookingDetails) {
        return bookingService.updateBooking(id, bookingDetails);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable(value = "id") String id) {
        bookingService.approveBooking(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
    }

    @DeleteMapping("/delete")
    public void deleteAllBookings() {
        bookingService.deleteAllBookings();
    }
}
