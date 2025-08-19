package com.codecademy.coffeeorders.controller;

import com.codecademy.coffeeorders.exception.OrderNotFoundException;
import com.codecademy.coffeeorders.model.CoffeeOrder;
import com.codecademy.coffeeorders.model.DrinkSize;
import com.codecademy.coffeeorders.service.CoffeeOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeOrdersController.class)
class CoffeeOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAddCoffeeOrder_withValidData_thenReturnsCreated() throws Exception {
        CoffeeOrder order = new CoffeeOrder();
        order.setCustomer("Test Customer");
        order.setBlend("Cappuccino");
        order.setSize(DrinkSize.LARGE);

        when(coffeeOrderService.createOrder(any(CoffeeOrder.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer").value("Test Customer"))
                .andExpect(jsonPath("$.blend").value("Cappuccino"));
    }

    @Test
    void whenAddCoffeeOrder_withInvalidData_thenReturnsBadRequest() throws Exception {
        CoffeeOrder order = new CoffeeOrder();
        order.setCustomer(""); // Invalid: customer is blank
        order.setBlend("Latte");
        order.setSize(DrinkSize.SMALL);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetOrderById_withValidId_thenReturnsOrder() throws Exception {
        CoffeeOrder order = new CoffeeOrder();
        order.setId(1L);
        order.setCustomer("Test Customer");
        order.setBlend("Espresso");

        when(coffeeOrderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customer", is("Test Customer")));
    }

    @Test
    void whenGetOrderById_withInvalidId_thenReturnsNotFound() throws Exception {
        // Correção: Lançar a exceção específica que retorna 404
        when(coffeeOrderService.getOrderById(99L)).thenThrow(new OrderNotFoundException("Order not found"));

        mockMvc.perform(get("/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFulfillOrder_withValidId_thenReturnsUpdatedOrder() throws Exception {
        CoffeeOrder fulfilledOrder = new CoffeeOrder();
        fulfilledOrder.setId(1L);
        fulfilledOrder.setFulfilled(true);

        when(coffeeOrderService.fulfillOrder(1L)).thenReturn(fulfilledOrder);

        mockMvc.perform(patch("/orders/1/fulfill"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fulfilled", is(true)));
    }

    @Test
    void whenGetAllOrders_thenReturnsOrderList() throws Exception {
        CoffeeOrder order1 = new CoffeeOrder();
        order1.setCustomer("Customer 1");
        CoffeeOrder order2 = new CoffeeOrder();
        order2.setCustomer("Customer 2");
        List<CoffeeOrder> orders = List.of(order1, order2);

        when(coffeeOrderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer", is("Customer 1")))
                .andExpect(jsonPath("$[1].customer", is("Customer 2")));
    }

    @Test
    void whenGetAllOrders_withNoOrders_thenReturnsEmptyList() throws Exception {
        when(coffeeOrderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
