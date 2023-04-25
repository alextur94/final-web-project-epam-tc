package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.OrderDao;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.api.*;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverterImpl;
import com.epam.jwd.service.converter.impl.CarConverterImpl;
import com.epam.jwd.service.converter.impl.MarkConverterImpl;
import com.epam.jwd.service.converter.impl.OrderConverterImpl;
import com.epam.jwd.service.dto.*;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDao orderDao = new OrderDaoImpl();
    private InsuranceService insuranceService = new InsuranceServiceImpl();
    private UserService userService = new UserServiceImpl();
    private CarService carService = new CarServiceImpl();
    private PriceService priceService = new PriceServiceImpl();
    private AccountService accountService = new AccountServiceImpl();
    private MarkService markService = new MarkServiceImpl();
    private Converter<Order, OrderDto, Integer> orderConverterImpl = new OrderConverterImpl();
    private Converter<Car, CarDto, Integer> carConverterImpl = new CarConverterImpl();
    private Converter<Account, AccountDto, Integer> accountConverterImpl = new AccountConverterImpl();
    private Converter<Mark, MarkDto, Integer> markConverterImpl = new MarkConverterImpl();
    private final Double LiterCostFuel = 1.95;
    private final Integer COUNT_MS_IN_MIN = 60000;
    private final Double SET_CURRENT_AMOUNT = 0.00;
    private final Long TIME_TO_PAY = (long) 10 * COUNT_MS_IN_MIN;
    private final String MAIN_ACCOUNT = "Admin";
    private final String TIMEOUT_TO_PAY_MSS = "Payment time expired";

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

    /**
     * Convert and return entity
     *
     * @param id
     * @return OrderDto entity
     */
    @Override
    public OrderDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + OrderServiceImpl.class);
        try {
            return orderConverterImpl.convert(orderDao.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieving all records
     *
     * @return List OrderDto
     */
    @Override
    public List<OrderDto> getAll() throws ServiceException {
        logger.info("get all method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        try {
            for (Order order : orderDao.findAll()) {
                orderDtoList.add(orderConverterImpl.convert(order));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderDtoList;
    }

    /**
     * Cancellation of the reservation by the administrator of the order.
     * Writing new data to the order. Returning money to the user.
     * Sending data for saving
     *
     * @param orderId, refusal
     * @return the boolean
     */
    @Override
    public Boolean cancelOrderAdmin(Integer orderId, String refusal) throws ServiceException {
        try {
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
            if (order.getPaymentStatus() == 1) {
                mainAdmin.setBalance(mainAdmin.getBalance() - amount);
                accountDto.setBalance(accountDto.getBalance() + amount);
            }
            Account person = accountConverterImpl.convert(accountDto);
            Account admin = accountConverterImpl.convert(mainAdmin);
            Car car = carConverterImpl.convert(carDto);
            orderDao.cancelOrderAdmin(admin, person, order, car);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * Creation of a new order. Creation of new insurance. Creation of a new damage mark.
     * Calculating the rental price. Sending data to save
     *
     * @param carDto, userId, count days rent, type insurance
     * @return the boolean
     */
    @Override
    public Boolean createOrder(CarDto carDto, Integer userId, Integer day, Byte type) throws ServiceException {
        logger.info("create method " + OrderServiceImpl.class);
        Mark mark = new Mark();
        String number = insuranceService.getGenerateNumber();
        String company = insuranceService.getCompany();
        Double amount = insuranceService.getAmountInsurance(type);
        Insurance insurance = new Insurance(type, number, company, amount);
        Double pledge = getPledge(carDto.getLevel());
        Double costInsurance = insuranceService.getCostInsurance(type);
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
        Car car = carConverterImpl.convert(carDto);
        Boolean resultCreateOrder = null;
        try {
            resultCreateOrder = orderDao.saveOrderMarkInsurance(mark, insurance, order, car);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (resultCreateOrder) {
            timerPay(order.getId());
        }
        return resultCreateOrder;
    }

    /**
     * A mark about the start of the car rental. Receiving new data for the order.
     *
     * @param orderId
     * @return the boolean
     */
    @Override
    public Boolean beginRent(Integer orderId) throws ServiceException {
        logger.info("begin rent method " + OrderServiceImpl.class);
        Order order = null;
        try {
            order = orderDao.findById(orderId);
            order.setStatus(Status.ACTIVE.getId());
            Date date = new Date();
            order.setRentStartDtm(date.getTime());
            order.setStartLevel(carService.getById(order.getCarId()).getEngineVolume());
            orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * Trip emulator. Getting the final data for the order. Calculation of the trip amount.
     * Calculation of the actual duration of the trip.
     * Transferring money between the administrator and the user.
     * Updating data in entities and closing a trip.
     *
     * @param orderId, amount damage, mark about damage
     * @return the boolean
     */
    @Override
    public Boolean endRent(Integer orderId, Double amountDamage, String markDesc) throws ServiceException {
        logger.info("end rent method " + OrderServiceImpl.class);
        Order order = null;
        try {
            order = orderDao.findById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        MarkDto markDto = markService.getById(order.getMarkId());

        if (amountDamage > 0) { setDescriptionMark(order, markDto, markDesc);}//Если есть ущерб, делаем пометку
        Double costInsuranse = 20.00;
        Double amountInsurance = insuranceService.getById(order.getInsuranceId()).getAmount(); //сумма возмещения ущерба от страховки
        Double sumDamage = amountMoreInsurance(amountDamage, amountInsurance); //определение суммы ущерба
        long timeRentalStart = order.getRentStartDtm(); //время начало аренды
        long timeRentalEnd = new Date().getTime(); //время конца аренды
        order.setStatus(Status.CLOSE.getId()); //устанавливаем ордеру статус закрты
        order.setRentalTime(realRentalTime(timeRentalStart, timeRentalEnd)); //реальное время аренды
        order.setRentEndDtm(timeRentalEnd); //устанавливаем время окончания аренды
        order.setEndLevel(getLevelFuelFromCar(order.getStartLevel()));// !!!метод вернет текущее значение бензина. Заменить на метод плучения даных с авто!!!
        CarDto carDto = carService.getById(order.getCarId()); //получаем авто
        carDto.setAvailable((byte) 1);//устанавливаем статус авто на "Досутпен"
        carDto.setEngineVolume(order.getEndLevel());//устанавливаем значение топлива из ордера
        PriceDto priceDto = priceService.getById(carDto.getPriceId());//получаем прайс
        Double currentAmount = order.getCurrentSum();//получаем сумму которую человек уже оплатил
        Double realRentTimeSum = realSum(order.getRentalTime(), priceDto);//расчитываем реальную сумму по времени аренды
        Double sumFuel = fuelDifference(order.getStartLevel(), order.getEndLevel());//получаем сумму за бензин (положительную/отрицательную)
        AccountDto accountDto = accountService.getById(userService.getById(order.getUserId()).getAccountId());//получем аккаунт
        Double realRentSum = realRentAmount(realRentTimeSum, sumFuel, costInsuranse, sumDamage);//подсчет суммы аренды
        order.setCurrentSum(realRentSum);//установка реальной суммы аренды
        AccountDto mainAdmin = accountService.getById(userService.getByLogin(MAIN_ACCOUNT).getAccountId());//берем аккаунт админа
        Double differentSum = currentAmount-realRentSum;//получаем разницу заплаченой суммы и фактической
        setBalanceAmount(mainAdmin, accountDto, differentSum);//устанавливаем новые суммы для аккаунтов
        Mark mark = markConverterImpl.convert(markDto);
        Car car = carConverterImpl.convert(carDto);
        Account account = accountConverterImpl.convert(accountDto);
        Account admin = accountConverterImpl.convert(mainAdmin);
        try {
            orderDao.finishSaveOrder(order, account, car, mark, admin);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

        public Boolean endTestRent(Integer orderId, Double amountDamage, String markDesc) throws ServiceException {

        return false;
    }

    /**
     * get list by status
     *
     * @param status order
     * @return list orderDto
     */
    @Override
    public List<OrderDto> getByStatus(Integer status) throws ServiceException {
        logger.info("get list by status method " + OrderServiceImpl.class);
        List<OrderDto> orderDtoList = new ArrayList<>();
        try {
            for (Order order : orderDao.findByStatus(status)) {
                orderDtoList.add(orderConverterImpl.convert(order));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderDtoList;
    }

    /**
     * Receiving orders for a user from the database for a certain amount
     *
     * @param userId
     * @return list orderDto
     */
    @Override
    public List<OrderDto> findCountOrders(Integer start, Integer count, Integer userId) throws ServiceException {
        logger.info("Find count orders by status method " + OrderServiceImpl.class);
        List<Order> listOrder = null;
        try {
            listOrder = orderDao.findCountOrdersInBase(start, count, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<OrderDto> listOrderDto = new ArrayList<>();
        for (Order order : listOrder) {
            listOrderDto.add(orderConverterImpl.convert(order));
        }
        return listOrderDto;
    }

    /**
     * Getting the number of orders for a specific order status
     *
     * @param status
     * @return the integer
     */
    @Override
    public Integer getCountRowByStatus(Integer status) throws ServiceException {
        logger.info("get count row by status method " + OrderServiceImpl.class);
        try {
            return orderDao.findByStatus(status).size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Approval of the application for the possibility of renting
     *
     * @param orderId
     */
    @Override
    public void approveOrder(Integer orderId) throws ServiceException {
        logger.info("approve order method " + OrderServiceImpl.class);
        Order order = null;
        try {
            order = orderDao.findById(orderId);
            order.setStatus(Status.READY.getId());
            orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Checking that only one order is active for the user
     *
     * @param userId
     */
    @Override
    public void onlyOne(Integer userId) throws ServiceException {
        logger.info("only one method " + OrderServiceImpl.class);
        try {
            orderDao.OnlyOne(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * We receive all orders for a certain status and put two entities in the map
     *
     * @param status
     * @return mapPerson
     */
    @Override
    public Map<Integer, AccountDto> unionUserAndAccount(Integer status) throws ServiceException {
        List<Order> orders = null;
        try {
            orders = orderDao.findByStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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

    /**
     * Choosing the cost of a deposit for a car, taking into account its class
     *
     * @param level
     * @return the double
     */
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

    /**
     * Obtaining a preliminary amount from the accounting of available data
     *
     * @param pledge, insurance amount
     * @return the double
     */
    private Double getCurrentSum(Double pledge, Double insurance, Integer day, Double sumDay) {
        logger.info("get sum method " + OrderServiceImpl.class);
        return pledge + insurance + day * sumDay;
    }

    /**
     * Converting from milliseconds to minutes
     *
     * @param timeMs
     * @return the integer
     */
    private Integer convertMsToMin(Long timeMs) {
        return (int) (timeMs / COUNT_MS_IN_MIN);
    }

    /**
     * Getting the difference in the amount of gasoline
     *
     * @param startFuel and endFuel
     * @return the double
     */
    private Double fuelDifference(int startFuel, int endFuel) {
        return (endFuel - startFuel) * LiterCostFuel;
    }


    /**
     * Receiving the difference in money if there is damage to the car, taking into account insurance
     *
     * @param damage, insurance
     * @return the double
     */
    private Double amountMoreInsurance(Double damage, Double insurance) {
        if (damage > insurance) {
            return damage - insurance;
        }
        return 0.00;
    }

    /**
     * Calculating the real trip amount
     *
     * @param realTime, priceDto
     * @return the double
     */
    private Double realSum(Integer realTime, PriceDto priceDto) {
        int days = (int) Math.ceil(realTime / 1440);
        int hour = (int) Math.ceil((realTime - days * 1440) / 60);
        if (hour > 6) {
            return (days + 1) * priceDto.getPricePerDay();
        } else {
            Double amountDays = days * priceDto.getPricePerDay();
            Double amountHours = hour * priceDto.getPricePerHour();
            return amountDays + amountHours;
        }
    }

    /**
     * Full calculation of the cost of the trip,
     * taking into account all parameters (the amount for the trip,
     * the difference in the amount for fuel, the amount after payment of insurance)
     *
     * @param timeSum, fuelSum, insuranceSum
     * @param insuranceSum
     * @return the double
     */
    private Double realRentAmount(Double timeSum, Double fuelSum, Double costInsurance, Double insuranceSum) {
        return timeSum + fuelSum + costInsurance + insuranceSum;
    }

    private void timerPay(Integer orderId) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Order order = null;
                try {
                    order = orderDao.findById(orderId);
                } catch (DaoException exception) {
                    exception.printStackTrace();
                }
                if (order.getPaymentStatus() != 1) {
                    try {
                        cancelOrderAdmin(orderId, TIMEOUT_TO_PAY_MSS);
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, TIME_TO_PAY);
    }

    /**
     * This method returns the difference between time start rent and end rent in minutes.
     *
     * @param startTime, endTime
     * @return the double
     */
    private Integer realRentalTime(Long startTime, Long endTime){
        return convertMsToMin(endTime - startTime);
    }

    private void setDescriptionMark(Order order, MarkDto markDto, String markDesc) {
        markDto.setDescription(markDesc);
        order.setStatus((byte) 1);
    }

    private Integer getLevelFuelFromCar(Integer startLevel){
        return startLevel;
    }

    private void setBalanceAmount(AccountDto mainAdmin, AccountDto accountDto, Double differentSum) {
        Double adminAmount = mainAdmin.getBalance();
        Double clientAmount = accountDto.getBalance();
        Double sumAccountsAmount = adminAmount + clientAmount;
        if (differentSum > 0) {
            mainAdmin.setBalance(adminAmount - differentSum);
            accountDto.setBalance(clientAmount + differentSum);
        } else {
            mainAdmin.setBalance(adminAmount + differentSum);
            accountDto.setBalance(clientAmount - differentSum);
        }
        adminAmount = mainAdmin.getBalance();
        clientAmount = accountDto.getBalance();
        if (sumAccountsAmount != adminAmount + clientAmount) {
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
