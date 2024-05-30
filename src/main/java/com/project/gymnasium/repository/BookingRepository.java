package com.project.gymnasium.repository;

import com.project.gymnasium.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
