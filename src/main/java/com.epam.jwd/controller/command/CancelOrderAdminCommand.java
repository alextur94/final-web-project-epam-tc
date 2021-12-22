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

public enum CancelOrderAdminCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(CancelOrderAdminCommand.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_ORDERS_PAGE;
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
        Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        String refusal = request.getParameter(Constant.REFUSAL_PARAM);
        Integer orderId = Integer.parseInt(request.getParameter(Constant.ORDER_ID_PARAM));
        try {
            orderService.cancelOrderAdmin(orderId, refusal);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_CANCEL_ORDER_ADMIN_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
