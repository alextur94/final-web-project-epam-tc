package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.PageConstant;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

public enum LoginCommand implements Command {
    INSTANCE;

    public static final String LOGIN_PARAM = "login";
    public static final String PASSWORD_PARAM = "password";
    public static final String ERROR_ATTRIB_NAME = "error";
    public static final String INVALID_MSS = "Wrong login or password";
    public static final String USER_NAME_ATTRIB_NAME = "userName";
    public static final String USER_LOGIN_ATTRIB_NAME = "userLogin";
    public static final String USER_ROLE_ATTRIB_NAME = "userRole";


    private static final CommandResponse LOGIN_ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return PageConstant.PATH_PAGE_LOGIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse LOGIN_SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return PageConstant.PATH_PAGE_MAIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String login = request.getParameter(LOGIN_PARAM);
        final String password = request.getParameter(PASSWORD_PARAM);
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
        request.setAttribute(ERROR_ATTRIB_NAME, INVALID_MSS);
        return LOGIN_ERROR_RESPONSE;
    }

    private CommandResponse addUserInfoToSession(CommandRequest request, UserDto userDto, AccountDto accountDto) {
        request.getCurrentSession().ifPresent(HttpSession::invalidate);
        final HttpSession session = request.createSession();
        session.setAttribute(USER_NAME_ATTRIB_NAME, accountDto.getName());
        session.setAttribute(USER_LOGIN_ATTRIB_NAME, userDto.getLogin());
        session.setAttribute(USER_ROLE_ATTRIB_NAME, accountDto.getRoleId());
        return LOGIN_SUCCESS_RESPONSE;
    }
}
