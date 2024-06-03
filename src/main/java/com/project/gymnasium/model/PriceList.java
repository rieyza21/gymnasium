package com.project.gymnasium.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "price_list",
    uniqueConstraints = {
                @UniqueConstraint(columnNames = {"bookingType", "userType"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private BookingType bookingType;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private BigDecimal hourlyRate;
    private BigDecimal dailyRate;


}
