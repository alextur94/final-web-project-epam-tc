package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.*;
import com.epam.jwd.service.dto.*;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl implements Service<OrderDto, Integer> {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final Double LiterCostFuel = 1.95;
    private final Integer COUNT_MS_IN_MIN = 60000;
    private final Double SET_CURRENT_AMOUNT = 0.00;
    private final String MAIN_ACCOUNT = "admin";
    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private final InsuranceServiceImpl insuranceService = new InsuranceServiceImpl();
    private final PriceServiceImpl priceService = new PriceServiceImpl();
    private final OrderConverter orderConverter = new OrderConverter();
    private final CarConverter carConverter = new CarConverter();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final CarServiceImpl carService = new CarServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final AccountConverter accountConverter = new AccountConverter();
    private final InsuranceConverter insuranceConverter = new InsuranceConverter();
    private final MarkConverter markConverter = new MarkConverter();
    private final MarkServiceImpl markService = new MarkServiceImpl();

    @Override
    public OrderDto create(OrderDto orderDto) throws ServiceException {
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
    public OrderDto getById(Integer id) throws ServiceException, DaoException {
        logger.info("get by id method " + OrderServiceImpl.class);
        return orderConverter.convert(orderDao.findById(id));
    }

    @Override
    public List<OrderDto> getAll() throws ServiceException, DaoException {
        logger.info("get all method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderDao.findAll()) {
            orderDtoList.add(orderConverter.convert(order));
        }
        return orderDtoList;
    }

    public Boolean cancelOrderAdmin(Integer userId, Integer orderId, String refusal) throws ServiceException, DaoException {
        Order order = orderDao.findById(orderId);
        UserDto userDto = userService.getById(order.getUserId());
        AccountDto accountDto = accountService.getById(userDto.getAccountId());
        CarDto carDto = carService.getById(order.getCarId());
        Double amount = order.getCurrentSum();
        order.setCurrentSum(SET_CURRENT_AMOUNT);
        order.setPledge(SET_CURRENT_AMOUNT);
        order.setRefusal(refusal);
        order.setStatus(Status.CLOSE.getId());
        carDto.setAvailable((byte) 1);
        AccountDto mainAdmin = accountService.getById(userService.getByLogin(MAIN_ACCOUNT).getAccountId());
        mainAdmin.setBalance(mainAdmin.getBalance() - amount);
        accountDto.setBalance(accountDto.getBalance() + amount);
        Account person = accountConverter.convert(accountDto);
        Account admin = accountConverter.convert(mainAdmin);
        Car car = carConverter.convert(carDto);
        orderDao.cancelOrderAdmin(admin, person, order, car);
        return true;
    }

    public Boolean createOrder(CarDto carDto, Integer userId, Integer day, Byte type) throws ServiceException, DaoException {
        logger.info("create method " + OrderServiceImpl.class);
        Mark mark = new Mark();
        String number = insuranceService.getGenerateNumber();
        String company = insuranceService.getCompany();
        Double amount = insuranceService.getAmountInsurance(type);
        Insurance insurance = new Insurance(type, number, company, amount);
        Double pledge = getPledge(carDto.getLevel());
        Double costInsurance = insuranceService.getCostInsurance(carDto.getLevel());
        Double sumDay = priceService.getById(carDto.getId()).getPricePerDay();
        Order order = new Order.Builder()
                .withStatus(Status.WAITING_PAYMENT.getId())
                .withRentalTime(day * 1440)
                .withPaymentStatus((byte) 0)
                .withPledge(pledge)
                .withCurrentSum(getCurrentSum(pledge, costInsurance, day, sumDay))
                .withUserId(userId)
                .withCarId(carDto.getId())
                .build();
        Car car = carConverter.convert(carDto);
        return orderDao.saveOrderMarkInsurance(mark, insurance, order, car);
    }

    public Boolean beginRent(Integer orderId) throws ServiceException, DaoException {
        logger.info("begin rent method " + OrderServiceImpl.class);
        Order order = orderDao.findById(orderId);
        order.setStatus(Status.ACTIVE.getId());
        Date date = new Date();
        order.setRentStartDtm(date.getTime());
        order.setStartLevel(carService.getById(order.getCarId()).getEngineVolume());
        orderDao.update(order);
        return true;
    }

    public Boolean endRent(Integer orderId, Double amountDamage, String markDesc) throws DaoException, ServiceException {
        logger.info("end rent method " + OrderServiceImpl.class);
        Order order = orderDao.findById(orderId);
        MarkDto markDto = markService.getById(order.getMarkId());
        if (amountDamage > 0) {
            markDto.setDescription(markDesc);
            order.setStatus((byte) 1);
        }
        Double amountInsurance = insuranceService.getById(order.getInsuranceId()).getAmount();
        Double sumMoreInsurance = amountMoreInsurance(amountDamage, amountInsurance);
        long timeStart = order.getRentStartDtm();
        long timeReal = rentalTimeSimulator(order.getRentalTime());
        order.setStatus(Status.CLOSE.getId());
        order.setRentalTime(convertMsMin(timeReal));
        order.setRentEndDtm(timeStart + timeReal);
        order.setEndLevel(fuelLevelRandom(order.getStartLevel()));
        CarDto carDto = carService.getById(order.getCarId());
        carDto.setAvailable((byte) 1);
        carDto.setEngineVolume(order.getEndLevel());
        PriceDto priceDto = priceService.getById(carDto.getPriceId());
        Double currentAmount = order.getCurrentSum();
        Double realTimeSum = realSum(order.getRentalTime(), priceDto);
        Double sumFuel = fuelDifference(order.getStartLevel(), order.getEndLevel());
        AccountDto accountDto = accountService.getById(userService.getById(order.getUserId()).getAccountId());
        Double finishSum = finishAmount(realTimeSum, sumFuel, sumMoreInsurance, currentAmount);
        order.setCurrentSum(finishSum);
        Double newAccountBalance = accountDto.getBalance() + finishSum;
        AccountDto mainAdmin = accountService.getById(userService.getByLogin(MAIN_ACCOUNT).getAccountId());
        mainAdmin.setBalance(mainAdmin.getBalance() - finishSum);
        accountDto.setBalance(newAccountBalance);
        Mark mark = markConverter.convert(markDto);
        Car car = carConverter.convert(carDto);
        Account account = accountConverter.convert(accountDto);
        Account admin = accountConverter.convert(mainAdmin);
        orderDao.finishSaveOrder(order, account, car, mark, admin);
        return true;
    }

    public List<OrderDto> getByUserId(Integer userId) throws DaoException, ServiceException {
        logger.info("get by user id method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderDao.findByUserId(userId)) {
            orderDtoList.add(orderConverter.convert(order));
        }
        return orderDtoList;
    }

    public List<OrderDto> getByStatus(Integer status) throws DaoException, ServiceException {
        logger.info("get list by status method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderDao.findByStatus(status)) {
            orderDtoList.add(orderConverter.convert(order));
        }
        return orderDtoList;
    }

    public List<OrderDto> findCountOrders(Integer start, Integer count, Integer userId) throws ServiceException, DaoException {
        logger.info("Find count orders by status method " + OrderServiceImpl.class);
        List<Order> listOrder = orderDao.findCountOrdersInBase(start, count, userId);
        List<OrderDto> listOrderDto = new ArrayList<>();
        for (Order order : listOrder) {
            listOrderDto.add(orderConverter.convert(order));
        }
        return listOrderDto;
    }

    public Integer getCountRowByStatus(Integer status) throws DaoException {
        logger.info("get count row by status method " + OrderServiceImpl.class);
        return orderDao.findByStatus(status).size();
    }

    public Boolean approveOrder(Integer orderId) throws DaoException {
        logger.info("approve order method " + OrderServiceImpl.class);
        Order order = orderDao.findById(orderId);
        order.setStatus(Status.READY.getId());
        return orderDao.update(order);
    }

    public Boolean onlyOne(Integer userId) throws DaoException {
        logger.info("only one method " + OrderServiceImpl.class);
        return orderDao.OnlyOne(userId);
    }

    private Double getPledge(Byte level) {
        logger.info("get pledge method " + OrderServiceImpl.class);
        Double pledge = 0.00;
        switch (level) {
            case 1: {
                pledge = 100.00;
                break;
            }
            case 2: {
                pledge = 220.00;
                break;
            }
            case 3: {
                pledge = 350.00;
                break;
            }
            case 4: {
                pledge = 500.00;
                break;
            }
        }
        return pledge;
    }

//    public String getTimeString(long msStart, long msEnd) {
//        logger.info("start rent method " + OrderServiceImpl.class);
//        long ms = msEnd - msStart;
//        int msInMin = (int) ms / 60000;
//        int days = (int) Math.ceil(msInMin / 1440);
//        int hour = (int) Math.ceil((msInMin - days * 1440) / 60);
//        int min = (int) Math.ceil(msInMin - ((days * 1440) + (hour * 60)));
//        return (days + " : " + hour + " : " + min);
//    }
//
//    public String getStartRentDataTime(long msStart) {
//        logger.info("start rent method " + OrderServiceImpl.class);
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return formater.format(msStart);
//    }

    public Map<Integer, AccountDto> testList(Integer status) throws DaoException, ServiceException {
        List<Order> orders = orderDao.findByStatus(status);
        List<UserDto> listUser = new ArrayList<>();
        for (Order order : orders) {
            listUser.add(userService.getById(order.getUserId()));
        }
        Map<Integer, AccountDto> mapPerson = new HashMap<>();
        for (UserDto userDto : listUser) {
            mapPerson.put(userDto.getId(), accountService.getById(userDto.getAccountId()));
        }
        return mapPerson;
    }

    private Double getCurrentSum(Double pledge, Double insurance, Integer day, Double sumDay) {
        logger.info("get sum method " + OrderServiceImpl.class);
        return pledge + insurance + day * sumDay;
    }

    private Integer convertMsMin(Long timeMs) {
        return (int) (timeMs / COUNT_MS_IN_MIN);
    }

    private Integer fuelLevelRandom(Integer startLevel) {
        return (int) (Math.random() * startLevel + (startLevel * 0.5));
    }

    private Double fuelDifference(int startFuel, int endFuel) {
        return (endFuel - startFuel) * LiterCostFuel;
    }

    private Long rentalTimeSimulator(Integer min) {
        Long ms = (long) min * COUNT_MS_IN_MIN;
        return (long) ((Math.random() * ms) + (ms * 0.5));
    }

    private Double amountMoreInsurance(Double damage, Double insurance) {
        if (damage > insurance) {
            return insurance - damage;
        }
        return 0.00;
    }

    private Double realSum(Integer realTime, PriceDto priceDto) {
        int days = (int) Math.ceil(realTime / 1440);
        int hour = (int) Math.ceil((realTime - days * 1440) / 60);
        if (hour > 6) {
            return (days + 1) * priceDto.getPricePerDay();
        } else {
            Double amountHours = hour * priceDto.getPricePerHour();
            Double amountDays = days * priceDto.getPricePerDay();
            return amountDays + amountDays;
        }
    }

    private Double finishAmount(Double timeSum, Double fuelSum, Double insuranceSum, Double currentSum) {
        return (currentSum + fuelSum) - (timeSum + insuranceSum);
    }
}
