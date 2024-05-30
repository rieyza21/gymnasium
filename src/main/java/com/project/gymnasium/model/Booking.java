package com.project.gymnasium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private Date bookingTimeStart;
        private Date bookingTimeEnd;
        private int duration;
        private String purpose;

        // Getters and Setters

        @PrePersist
        protected void onCreate() {
                bookingTimeStart = new Date();
                bookingTimeEnd = new Date(bookingTimeStart.getTime() + duration * 60 * 60 * 1000);
        }
}