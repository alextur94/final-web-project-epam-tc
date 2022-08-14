package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public enum RegistrationCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_REGISTRATION_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_LOGIN_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    public final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        final String login = request.getParameter(Constant.LOGIN_PARAM);
        final String email = request.getParameter(Constant.EMAIL_PARAM);
        final String password = request.getParameter(Constant.PASSWORD_PARAM);
        try {
            UserDto userDto = new UserDto(login, password);
            AccountDto accountDto = new AccountDto.Builder().withEmail(email).build();
            userService.savePerson(userDto, accountDto);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.NEW_ACCOUNT_WAS_CREATE);
            return SUCCESS_RESPONSE;
        } catch (DaoException | ServiceException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
