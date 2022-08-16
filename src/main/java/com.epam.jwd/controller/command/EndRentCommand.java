package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum EndRentCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(EndRentCommand.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_ACTIVE_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_ACTIVE_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        final Integer orderId = (Integer) session.getAttribute(Constant.ORDER_ID_PARAM);
        final Double amountDamage = Double.parseDouble(request.getParameter(Constant.DAMAGE_PARAM));
        String description = request.getParameter(Constant.DESCRIPTION_PARAM);
        try {
            orderService.endRent(orderId, amountDamage, description);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_END_RENT_MSS);
            return SUCCESS_RESPONSE;
        } catch (ServiceException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
