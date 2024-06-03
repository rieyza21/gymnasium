package com.project.gymnasium.service;

import com.project.gymnasium.model.BookingStatus;
import com.project.gymnasium.model.User;
import com.project.gymnasium.model.Booking;
import com.project.gymnasium.repository.BookingRepository;
import com.project.gymnasium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private MailService mailService;

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
            booking.setUserType(user.getType());
            booking.setBookingStatus(BookingStatus.PENDING);
            String message = String.format("Dear %s,\n\nYour booking has been created and is pending approval.\n\nBooking Details:\n%s\n\nBest Regards,\nGymnasium Team",
                    user.getName(), booking.toString());
            mailService.sendMail(user.getEmail(), "Booking Created", message);
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
        booking.setPurpose(bookingDetails.getPurpose());

        return bookingRepository.save(booking);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void approveBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking);
        String message = String.format("Dear %s,\n\nYour booking has been approved.\n\nBooking Details:\n%s\n\nBest Regards,\nGymnasium Team",
                booking.getUser().getName(), booking.toString());
        mailService.sendMail(booking.getUser().getEmail(), "Booking Approved", message);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }
}
