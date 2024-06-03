package com.project.gymnasium.repository;

import com.project.gymnasium.model.Booking;
import com.project.gymnasium.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUser(User user);
}
