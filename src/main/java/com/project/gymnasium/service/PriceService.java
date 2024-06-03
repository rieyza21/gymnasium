package com.project.gymnasium.service;


import com.project.gymnasium.model.PriceList;
import com.project.gymnasium.model.User;
import com.project.gymnasium.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public PriceList addPrice(PriceList price) {
        return priceRepository.save(price);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PriceList updatePrice(Long id, PriceList priceDetails) {
        PriceList price = priceRepository.findById(BigDecimal.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        price.setHourlyRate(priceDetails.getHourlyRate());
        price.setDailyRate(priceDetails.getDailyRate());
        return priceRepository.save(price);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePrice(BigDecimal id) {
        priceRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllPrices() {
        priceRepository.deleteAll();
    }

    public List<PriceList> getPrice(PriceList price) {
        return priceRepository.findAll();
    }

    public PriceList getPriceById(BigDecimal id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Price not found"));
    }
}
