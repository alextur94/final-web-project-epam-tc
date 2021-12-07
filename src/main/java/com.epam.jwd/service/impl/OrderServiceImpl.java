package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.InsuranceDaoImpl;
import com.epam.jwd.dao.impl.MarkDaoImpl;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.InsuranceConverter;
import com.epam.jwd.service.converter.impl.MarkConverter;
import com.epam.jwd.service.converter.impl.OrderConverter;
import com.epam.jwd.service.dto.InsuranceDto;
import com.epam.jwd.service.dto.MarkDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.InsuranceValidator;
import com.epam.jwd.service.validator.impl.MarkValidator;
import com.epam.jwd.service.validator.impl.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements Service<OrderDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final Dao<Order, Integer> daoOrder = new OrderDaoImpl();
    private final Dao<Mark, Integer> daoMark = new MarkDaoImpl();
    private final Dao<Insurance, Integer> daoInsurance = new InsuranceDaoImpl();
    private final Converter<Order, OrderDto, Integer> converterOrder = new OrderConverter();
    private final Converter<Mark, MarkDto, Integer> converterMark = new MarkConverter();
    private final Converter<Insurance, InsuranceDto, Integer> converterInsurance = new InsuranceConverter();
    private final Validator<OrderDto, Integer> validatorOrder = new OrderValidator();
    private final Validator<MarkDto, Integer> validatorMark = new MarkValidator();
    private final Validator<InsuranceDto, Integer> validatorInsurance = new InsuranceValidator();

    @Override
    public OrderDto save(OrderDto orderDto) throws ServiceException, SQLException {
        logger.info("save method " + OrderServiceImpl.class);
        validatorOrder.validate(orderDto);
        Order order = converterOrder.convert(orderDto);
        return converterOrder.convert(daoOrder.save(order));
    }

    @Override
    public Boolean update(OrderDto value) throws ServiceException {
        return null;
    }

    @Override
    public Boolean delete(OrderDto value) throws ServiceException {
        return null;
    }

    @Override
    public OrderDto getById(Integer id) throws ServiceException {
        return null;
    }

    @Override
    public List<OrderDto> getAll() throws ServiceException {
        return null;
    }
}
