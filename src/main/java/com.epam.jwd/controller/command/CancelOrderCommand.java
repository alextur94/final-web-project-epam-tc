package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.converter.impl.OrderConverterImpl;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import com.epam.jwd.service.validator.impl.OrderValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum CancelOrderCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(CancelOrderCommand.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_USER_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_USER_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final CarServiceImpl carService = new CarServiceImpl();
    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final OrderValidatorImpl orderValidatorImpl = new OrderValidatorImpl();
    private final OrderConverterImpl orderConverterImpl = new OrderConverterImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        final Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        final Integer orderId = (Integer) session.getAttribute(Constant.ORDER_ID_PARAM);
        try {
            Integer accountId = userService.getById(userId).getAccountId();
            AccountDto accountDto = accountService.getById(accountId);
            Order order = orderDao.findById(orderId);
            orderValidatorImpl.validate(orderConverterImpl.convert(order));
            CarDto carDto = carService.getById(order.getCarId());
            accountService.cancelOrder(accountDto, carDto, order);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_APPLICATION_CANCELED_MSS);
            return SUCCESS_RESPONSE;
        } catch (ServiceException | DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }

}
