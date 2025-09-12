package com.kushi.in.app.service;

import com.kushi.in.app.model.OrderDTO;
import com.kushi.in.app.model.OrderRequest;
import com.kushi.in.app.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request);
    List<OrderDTO> getOrdersByUserId(Long userId); // return DTO instead of entity
}
