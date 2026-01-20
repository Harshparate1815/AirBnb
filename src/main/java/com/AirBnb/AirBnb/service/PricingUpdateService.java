package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.entity.Hotel;
import com.AirBnb.AirBnb.entity.HotelMinPrice;
import com.AirBnb.AirBnb.entity.Inventory;
import com.AirBnb.AirBnb.repository.HotelMinPriceRepository;
import com.AirBnb.AirBnb.repository.HotelRepository;
import com.AirBnb.AirBnb.repository.InventoryRepository;
import com.AirBnb.AirBnb.strategy.PricingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Slf4j
@Transactional
public class PricingUpdateService {
        // Scheduler to Update the inventory and HotelMinPrice tables every hour
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;


    public PricingUpdateService(HotelRepository hotelRepository, InventoryRepository inventoryRepository, HotelMinPriceRepository hotelMinPriceRepository, PricingService pricingService) {
        this.hotelRepository = hotelRepository;
        this.inventoryRepository = inventoryRepository;
        this.hotelMinPriceRepository = hotelMinPriceRepository;
        this.pricingService = pricingService;
    }
//    @Scheduled(cron="*/5 * * * * *")
    @Scheduled(cron="0 0 * * * *")
    public void updatePrice()               // The method to update prices of all hotels int the database
    {
        int page=0;
        int batchSize=100;                  // Number of records fetched per query(100 hotels at a time)
        while(true)
        {
            Page<Hotel> hotelPage=hotelRepository.findAll(PageRequest.of(page,batchSize));
            if(hotelPage.isEmpty())
            {
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrice);
            page++;
        }
    }

    private void updateHotelPrice(Hotel hotel)
    {
//        log.info("Updating hotel prices for hotel ID:{}",hotel.getId());
        LocalDate startDate=LocalDate.now();
        LocalDate endDate=LocalDate.now().plusYears(1);
        List<Inventory> inventoryList=inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);
        updateInventoryPrices(inventoryList);
        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);
    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        // compute minimum price per day for the hotel
        Map<LocalDate, BigDecimal> dailyMinPrices=inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));

        //Prepare HotelPrice entities in bulk
        List<HotelMinPrice> hotelPrices=new ArrayList<>();
        dailyMinPrices.forEach((date,price)->{
            HotelMinPrice hotelPrice=hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                    .orElse(new HotelMinPrice(hotel,date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);

        });
        hotelMinPriceRepository.saveAll(hotelPrices);
    }

    private void updateInventoryPrices(List<Inventory> inventoryList)
    {
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice=pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }

}
