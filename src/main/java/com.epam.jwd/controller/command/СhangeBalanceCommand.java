package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum СhangeBalanceCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(СhangeBalanceCommand.class);
    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_USER_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_USER_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private final UserServiceImpl userService = new UserServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        try {
            Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
            Double balance = Double.parseDouble(request.getParameter(Constant.BALANCE_CHANGE_PARAM));
            Integer accountId;
            AccountDto accountDto;
            accountId = userService.getById(userId).getAccountId();
            accountDto = accountService.getById(accountId);
            accountDto.setBalance(accountDto.getBalance() + balance);
            accountService.update(accountDto);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_UPDATE_BALANCE_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException | NumberFormatException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
