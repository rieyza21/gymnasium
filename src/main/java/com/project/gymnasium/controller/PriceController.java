package com.project.gymnasium.controller;

import com.project.gymnasium.model.PriceList;
import com.project.gymnasium.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping("/add")
    public PriceList addPrice(@RequestBody PriceList price) {
        return priceService.addPrice(price);
    }

    @PutMapping("/update/{id}")
    public PriceList updatePrice(@PathVariable Long id, @RequestBody PriceList price) {
        return priceService.updatePrice(id, price);
    }

    @GetMapping("/get")
    public List<PriceList> getPrice(@RequestBody PriceList price) {
        return priceService.getPrice(price);
    }

    @GetMapping("/get/{id}")
    public PriceList getPriceById(@PathVariable BigDecimal id) {
        return priceService.getPriceById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePrice(@PathVariable BigDecimal id) {
        priceService.deletePrice(id);
    }

    @DeleteMapping("/delete")
    public void deleteAllPrice() {
        priceService.deleteAllPrices();
    }

}
