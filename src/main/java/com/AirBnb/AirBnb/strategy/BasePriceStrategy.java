package com.AirBnb.AirBnb.strategy;

import com.AirBnb.AirBnb.entity.Inventory;

import java.math.BigDecimal;

public class BasePriceStrategy implements PricingStrategy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory)
    {
        return inventory.getRoom().getBasePrice();
    }
}
