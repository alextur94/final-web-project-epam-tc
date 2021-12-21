package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum PayCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(PayCommand.class);

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

    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        try {
            final Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
            final Integer orderId = Integer.parseInt(request.getParameter(Constant.ORDER_ID_PARAM));
            Integer accountId = userService.getById(userId).getAccountId();
            AccountDto accountDto = accountService.getById(accountId);
            Order order = orderDao.findById(orderId);
            if (order.getUserId() != userId || order.getPaymentStatus() != 0){
                session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_LESS_ROOTS_MSS);
                return ERROR_RESPONSE;
            }
            Boolean result = accountService.transferAmountAccountAdmin(accountDto, order);
            if (!result) {
                session.setAttribute(Constant.ERROR_PARAM, Constant.SUCCESS_FAIL_PAY_MSS);
                return ERROR_RESPONSE;
            }
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_PAY_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException | NumberFormatException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }

}
