package com.epam.jwd.service.impl;

import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.dao.impl.InsuranceDaoImpl;
import com.epam.jwd.dao.impl.MarkDaoImpl;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.CarConverter;
import com.epam.jwd.service.converter.impl.InsuranceConverter;
import com.epam.jwd.service.converter.impl.MarkConverter;
import com.epam.jwd.service.converter.impl.OrderConverter;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.InsuranceDto;
import com.epam.jwd.service.dto.MarkDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.InsuranceValidator;
import com.epam.jwd.service.validator.impl.MarkValidator;
import com.epam.jwd.service.validator.impl.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements Service<OrderDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final PriceServiceImpl priceService = new PriceServiceImpl();
    private final OrderDaoImpl daoOrder = new OrderDaoImpl();
    private final MarkDaoImpl daoMark = new MarkDaoImpl();
    private final InsuranceDaoImpl daoInsurance = new InsuranceDaoImpl();
    private final CarDaoImpl daoCar = new CarDaoImpl();
    private final OrderConverter converterOrder = new OrderConverter();
    private final MarkConverter converterMark = new MarkConverter();
    private final InsuranceConverter converterInsurance = new InsuranceConverter();
    private final CarConverter converterCar = new CarConverter();
    private final OrderValidator validatorOrder = new OrderValidator();
    private final MarkValidator validatorMark = new MarkValidator();
    private final InsuranceValidator validatorInsurance = new InsuranceValidator();
    private final InsuranceServiceImpl insuranceService = new InsuranceServiceImpl();

    @Override
    public OrderDto create(OrderDto orderDto) throws ServiceException, SQLException {
        return null;
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
        logger.info("get all method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : daoOrder.findAll()) {
            orderDtoList.add(converterOrder.convert(order));
        }
        return orderDtoList;
    }

    public Boolean createOrder(CarDto carDto, Integer userId, Integer day, Byte type) throws SQLException, ServiceException {
        logger.info("create method " + OrderServiceImpl.class);
        MarkDto markDto = new MarkDto();
        String number = insuranceService.generateNumber();
        String company = insuranceService.getCompany();
        Double amount = insuranceService.getAmountInsurance(type);
        InsuranceDto insuranceDto = new InsuranceDto(type, number, company, amount);
        Double pledge = getPledge(carDto.getLevel());
        Double costInsurance = insuranceService.costInsurance(carDto.getLevel());
        Double sumDay = priceService.getById(carDto.getId()).getPricePerDay();
        Order order = new Order.Builder()
            .withStatus((byte)1)
            .withRentalTime(0)
            .withPaymentStatus((byte)0)
            .withPledge(pledge)
            .withCurrentSum(getCurrentSum(pledge, costInsurance, day, sumDay))
            .withUserId(userId)
            .withCarId(carDto.getId())
            .build();
        Car car = converterCar.convert(carDto);
        Mark mark = converterMark.convert(markDto);
        Insurance insurance = converterInsurance.convert(insuranceDto);
        daoOrder.saveMarkOrderInsurance(mark ,insurance ,order , car);
        return true;
    }

    public List<OrderDto> getByUserId(Integer userId) throws SQLException {
        logger.info("get by user id method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : daoOrder.findByUserId(userId)) {
            orderDtoList.add(converterOrder.convert(order));
        }
        return orderDtoList;
    }

    public Boolean onlyOne(Integer userId){
        logger.info("only one method " + OrderServiceImpl.class);
        return daoOrder.OnlyOne(userId);
    }

    private Double getPledge(Byte level){
        Double pledge = 0.00;
        switch (level) {
            case 1: {pledge = 100.00; break;}
            case 2: {pledge = 220.00; break;}
            case 3: {pledge = 350.00; break;}
            case 4: {pledge = 500.00; break;}
        }
        return pledge;
    }

    private Double getCurrentSum(Double pledge, Double insurance, Integer day, Double sumDay){
        return pledge+insurance+day*sumDay;
    }
}
