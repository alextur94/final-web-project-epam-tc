package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum NewOrderCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(NewOrderCommand.class);

    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        try {
            final Integer carId = Integer.parseInt(request.getParameter(Constant.CAR_ID_PARAM));
            final Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
            final Integer day = Integer.parseInt(request.getParameter(Constant.DAY_PARAM));
            try {
                orderService.onlyOne(userId);
            } catch (ServiceException exception) {
                logger.error(exception);
                session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
                return ERROR_RESPONSE;
            }
            if (0 >= day || day > 30) {
                session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_NUMBER_DAYS_MSS);
                return ERROR_RESPONSE;
            }
            final Byte insuranceType = Byte.parseByte(request.getParameter(Constant.INSURANCE_TYPE_PARAM));
            CarDto carDto = null;
            carDto = carService.getById(carId);
            orderService.createOrder(carDto, userId, day, insuranceType);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_CAR_BOOKING_MSS);
            return SUCCESS_RESPONSE;
        } catch (ServiceException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        } catch (NumberFormatException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_FIELDS_IS_NULL_MSS);
            return ERROR_RESPONSE;
        }
    }

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
}
