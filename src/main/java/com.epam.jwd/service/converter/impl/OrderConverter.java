package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderConverter implements Converter<Order, OrderDto, Integer> {
    @Override
    public Order convert(OrderDto orderDto) {
        return new Order.Builder()
                .withId(orderDto.getId())
                .withStatus(orderDto.getStatus())
                .withRentalTime(Integer.parseInt(orderDto.getRentalTime()))
                .withPaymentStatus(orderDto.getPaymentStatus())
                .withRentStartDtm(Long.parseLong(orderDto.getRentStartDtm()))
                .withRentEndDtm(Long.parseLong(orderDto.getRentEndDtm()))
                .withStartLevel(orderDto.getStartLevel())
                .withEndLevel(orderDto.getEndLevel())
                .withRefusal(orderDto.getRefusal())
                .withPledge(orderDto.getPledge())
                .withCurrentSum(orderDto.getCurrentSum())
                .withStatusMark(orderDto.getStatusMark())
                .withUserId(orderDto.getUserId())
                .withCarId(orderDto.getCarId())
                .withMarkId(orderDto.getMarkId())
                .withInsuranceId(orderDto.getInsuranceId())
                .build();
    }

    @Override
    public OrderDto convert(Order order) throws ServiceException {
        return new OrderDto.Builder()
                .withId(order.getId())
                .withStatus(order.getStatus())
                .withRentalTime(convertMinToTime(order.getRentalTime()))
                .withPaymentStatus(order.getPaymentStatus())
                .withRentStartDtm(convertMsToFormattedDate(order.getRentStartDtm()))
                .withRentEndDtm(convertMsToFormattedDate(order.getRentEndDtm()))
                .withStartLevel(order.getStartLevel())
                .withEndLevel(order.getEndLevel())
                .withRefusal(order.getRefusal())
                .withPledge(order.getPledge())
                .withCurrentSum(order.getCurrentSum())
                .withStatusMark(order.getStatusMark())
                .withUserId(order.getUserId())
                .withCarId(order.getCarId())
                .withMarkId(order.getMarkId())
                .withInsuranceId(order.getInsuranceId())
                .build();
    }

    public String convertMsToFormattedDate(long milliSeconds) {
        if (milliSeconds != 0) {
            String dateFormat = "dd-MM-yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return simpleDateFormat.format(calendar.getTime());
        }
        return "";
    }

    public String convertMinToTime(int time) {
        int day = (int) Math.ceil(time / 1440);
        int hour = (int) Math.ceil((time - (day * 1440)) / 60);
        int min = (int) Math.ceil(time - ((day * 1440) + (hour * 60)));
        return "day " + day + " hour " + hour + " min " + min;
    }
}
