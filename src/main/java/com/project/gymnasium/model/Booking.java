package com.project.gymnasium.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.gymnasium.config.RangeDeserializer;
import com.project.gymnasium.config.RangeSerializer;
import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import io.hypersistence.utils.hibernate.type.range.Range;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        @JsonBackReference
        private User user;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        @Enumerated(value = EnumType.STRING)
        @Column(nullable = false)
        private BookingType bookingType;

        @Enumerated(value = EnumType.STRING)
        private BookingStatus bookingStatus;

        @Type(PostgreSQLRangeType.class)
        @Column(name = "booking_dates", columnDefinition = "tsrange")
        @JsonDeserialize(using = RangeDeserializer.class)
        @JsonSerialize(using = RangeSerializer.class)
        private Range<LocalDateTime> bookingDates;

        @Transient
        private BigDecimal hourlyRate;

        @Transient
        private BigDecimal dailyRate;

        @Transient
        private BigDecimal totalHourlyPrice;

        @Transient
        private BigDecimal totalDailyPrice;

        @Column(columnDefinition = "money")
        private BigDecimal totalPrice;

        private String purpose;

        @PrePersist
        @PreUpdate
        protected void onCreateAndUpdate() {

                calculatePrices();

                if (user != null) {
                       userType = user.getType();
                }
        }


        public void calculatePrices() {
                if (bookingDates != null && bookingType != null) {
                        LocalDateTime start = bookingDates.lower();
                        LocalDateTime end = bookingDates.upper();
                        long durationInHours = Duration.between(start, end).toHours();
                        long durationInDays = Duration.between(start, end).toDays();

                        if (bookingType.equals(BookingType.GYMNASIUM)) {
                                setHourlyRate(new BigDecimal("30000"));
                                setDailyRate(new BigDecimal("300000"));
                        } else if (bookingType.equals(BookingType.BASKETBALL_FIELD)) {
                                setHourlyRate(new BigDecimal("20000"));
                                setDailyRate(new BigDecimal("200000"));
                        } else if (bookingType.equals(BookingType.TENNIS_TABLE)) {
                                setHourlyRate(new BigDecimal("10000"));
                                setDailyRate(new BigDecimal("100000"));
                        }

                        totalHourlyPrice = hourlyRate.multiply(BigDecimal.valueOf(durationInHours));
                        totalDailyPrice = dailyRate.multiply(BigDecimal.valueOf(durationInDays));

                        totalPrice = totalHourlyPrice.add(totalDailyPrice);

                        if (userType != null && userType.equals(userType.IPB)) {
                                totalPrice = totalPrice.multiply(new BigDecimal("0.8"));
                        }

                        setTotalPrice(totalPrice);
                }
        }

}