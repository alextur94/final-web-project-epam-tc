package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.OrderDto;

public class OrderConverter implements Converter<Order, OrderDto, Integer> {
    @Override
    public Order convert(OrderDto orderDto) {
        return new Order.Builder()
                .withId(orderDto.getId())
                .withStatus(orderDto.getStatus())
                .withRentalTime(orderDto.getRentalTime())
                .withPaymentStatus(orderDto.getPaymentStatus())
                .withRentStartDtm(orderDto.getRentStartDtm())
                .withRentEndDtm(orderDto.getRentEndDtm())
                .withStartLevel(orderDto.getStartLevel())
                .withEndLevel(orderDto.getEndLevel())
                .withRefusal(orderDto.getRefusal())
                .withPledge(orderDto.getPledge())
                .withCurrentSum(orderDto.getCurrentSum())
                .withRealSum(orderDto.getRealSum())
                .withStatusMark(orderDto.getStatusMark())
                .withUserId(orderDto.getUserId())
                .withCarId(orderDto.getCarId())
                .withMarkId(orderDto.getMarkId())
                .withInsuranceId(orderDto.getInsuranceId())
                .build();
    }

    @Override
    public OrderDto convert(Order order) {
        return new OrderDto.Builder()
                .withId(order.getId())
                .withStatus(order.getStatus())
                .withRentalTime(order.getRentalTime())
                .withPaymentStatus(order.getPaymentStatus())
                .withRentStartDtm(order.getRentStartDtm())
                .withRentEndDtm(order.getRentEndDtm())
                .withStartLevel(order.getStartLevel())
                .withEndLevel(order.getEndLevel())
                .withRefusal(order.getRefusal())
                .withPledge(order.getPledge())
                .withCurrentSum(order.getCurrentSum())
                .withRealSum(order.getRealSum())
                .withStatusMark(order.getStatusMark())
                .withUserId(order.getUserId())
                .withCarId(order.getCarId())
                .withMarkId(order.getMarkId())
                .withInsuranceId(order.getInsuranceId())
                .build();
    }
}
