package com.project.gymnasium.service;

import com.project.gymnasium.model.User;
import com.project.gymnasium.model.Booking;
import com.project.gymnasium.repository.BookingRepository;
import com.project.gymnasium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Booking booking) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found in the database"));
            booking.setUser(user);
        } else {
            throw new IllegalStateException("Current user not found");
        }

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
