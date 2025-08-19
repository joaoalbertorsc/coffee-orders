package com.codecademy.coffeeorders.controller;

import com.codecademy.coffeeorders.model.CoffeeOrder;
import com.codecademy.coffeeorders.service.CoffeeOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequestMapping("/orders")
@RestController
public class CoffeeOrdersController {
    private final CoffeeOrderService coffeeOrderService;

    public CoffeeOrdersController(CoffeeOrderService coffeeOrderService) {
        this.coffeeOrderService = coffeeOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder addCoffeeOrder(@Valid @RequestBody CoffeeOrder coffeeOrder) {
        return coffeeOrderService.createOrder(coffeeOrder);
    }

    @GetMapping("/{id}")
    public CoffeeOrder getCoffeeOrder(@PathVariable Long id) {
        return coffeeOrderService.getOrderById(id);
    }

    // Using PATCH is more semantically correct for a partial update.
    // A more explicit path also improves clarity.
    @PatchMapping("/{id}/fulfill")
    public CoffeeOrder fulfillCoffeeOrder(@PathVariable Long id) {
        return coffeeOrderService.fulfillOrder(id);
    }

    @GetMapping
    public Iterable<CoffeeOrder> getAllOrders() {
        return coffeeOrderService.getAllOrders();
    }
}
