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

public enum BrginRentCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(BrginRentCommand.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_READY_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_READY_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer orderId = (Integer) session.getAttribute(Constant.ORDER_ID_PARAM);
        try {
            orderService.beginRent(orderId);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_SAVE_INFO_ACCOUNT_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
