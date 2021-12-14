package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum LoginCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    final UserServiceImpl userService = new UserServiceImpl();


    private static final CommandResponse LOGIN_ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_LOGIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse LOGIN_SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_MAIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String login = request.getParameter(Constant.LOGIN_PARAM);
        final String password = request.getParameter(Constant.PASSWORD_PARAM);
        try {
            UserDto userDto = userService.checkLoginPassword(login, password);
            if (userDto == null) {
                return prepareErrorPage(request);
            }
            AccountDto accountDto = new AccountServiceImpl().getById(userDto.getAccountId());
            return addUserInfoToSession(request, userDto, accountDto);
        } catch (ServiceException e) {
            throw new ServiceException();
        }
    }

    private CommandResponse prepareErrorPage(CommandRequest request) {
        request.setAttribute(Constant.ERROR_ATTRIB_NAME, Constant.INVALID_MSS);
        return LOGIN_ERROR_RESPONSE;
    }

    private CommandResponse addUserInfoToSession(CommandRequest request, UserDto userDto, AccountDto accountDto) {
        request.getCurrentSession().ifPresent(HttpSession::invalidate);
        final HttpSession session = request.createSession();
        session.setAttribute(Constant.USER_ID_PARAM, userDto.getId());
        session.setAttribute(Constant.USER_LOGIN_ATTRIB_NAME, userDto.getLogin());
        session.setAttribute(Constant.USER_ROLE_ATTRIB_NAME, accountDto.getRole());
        return LOGIN_SUCCESS_RESPONSE;
    }
}
