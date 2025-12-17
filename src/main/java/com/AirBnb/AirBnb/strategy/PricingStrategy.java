package com.AirBnb.AirBnb.strategy;

import com.AirBnb.AirBnb.entity.Inventory;

import java.math.BigDecimal;
public interface PricingStrategy{

    public BigDecimal calculatePrice(Inventory inventory);
}
