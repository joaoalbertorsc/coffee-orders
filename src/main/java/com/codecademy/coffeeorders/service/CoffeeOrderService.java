package com.codecademy.coffeeorders.service;

import com.codecademy.coffeeorders.model.CoffeeOrder;
import com.codecademy.coffeeorders.repository.CoffeeOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CoffeeOrderService {

    private final CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrderService(CoffeeOrderRepository coffeeOrderRepository) {
        this.coffeeOrderRepository = coffeeOrderRepository;
    }

    public Iterable<CoffeeOrder> getAllOrders() {
        return this.coffeeOrderRepository.findAll();
    }

    public CoffeeOrder getOrderById(Long id) {
        return coffeeOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public CoffeeOrder createOrder(CoffeeOrder coffeeOrder) {
        // Business logic is now contained here
        coffeeOrder.setFulfilled(false);
        return coffeeOrderRepository.save(coffeeOrder);
    }

    public CoffeeOrder fulfillOrder(Long id) {
        CoffeeOrder orderToFulfill = getOrderById(id);

        if (orderToFulfill.isFulfilled()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order has already been fulfilled");
        }

        orderToFulfill.setFulfilled(true);
        return coffeeOrderRepository.save(orderToFulfill);
    }
}