package com.project.gymnasium.repository;

import com.project.gymnasium.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PriceRepository extends JpaRepository<PriceList, BigDecimal> {

}
