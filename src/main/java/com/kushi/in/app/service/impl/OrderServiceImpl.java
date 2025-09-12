package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.OrderRepository;
import com.kushi.in.app.dao.UserRepository;
import com.kushi.in.app.entity.Order;
import com.kushi.in.app.entity.OrderItem;
import com.kushi.in.app.entity.User;
import com.kushi.in.app.model.OrderDTO;
import com.kushi.in.app.model.OrderRequest;
import com.kushi.in.app.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Order createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(request.getTotalAmount());
        order.setTax(request.getTax());
        order.setSavings(request.getSavings());
        order.setPromoCode(request.getPromoCode());

        List<OrderItem> items = request.getCartItems().stream().map(ci -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setServiceId(ci.getServiceId());
            item.setServiceName(ci.getServiceName());
            item.setPrice(ci.getPrice());
            item.setQuantity(ci.getQuantity());
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        return orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setDate(order.getCreatedAt());
            dto.setServices(
                    order.getItems().stream()
                            .map(OrderItem::getServiceName)
                            .collect(Collectors.toList())
            );
            dto.setAmount(order.getTotalAmount());
            dto.setAddress(order.getUser().getAddress());
            dto.setRating(null); // or set real rating if exists
            return dto;
        }).collect(Collectors.toList());
    }


}
