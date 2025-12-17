package com.AirBnb.AirBnb.strategy;

import com.AirBnb.AirBnb.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service

public class SurgePricingStrategy implements PricingStrategy{
    private final PricingStrategy wrapped;

    public SurgePricingStrategy(PricingStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
