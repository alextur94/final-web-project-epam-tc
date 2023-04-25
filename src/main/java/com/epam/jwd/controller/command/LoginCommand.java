package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
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
    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_LOGIN_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_MAIN_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        final String lang = (String) session.getAttribute(Constant.LANG_PARAM);
        final String login = request.getParameter(Constant.LOGIN_PARAM);
        final String password = request.getParameter(Constant.PASSWORD_PARAM);
        if (login == null || password == null) {
            session.setAttribute(Constant.ERROR_PARAM, Constant.FIELDS_NOT_BE_NULL_MSS);
            return ERROR_RESPONSE;
        }
        try {
            UserDto userDto = userService.checkLoginPassword(login, password);
            AccountDto accountDto = new AccountServiceImpl().getById(userDto.getAccountId());
            return successResponse(request, userDto, accountDto, lang);
        } catch (ServiceException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        }
    }

    private CommandResponse successResponse(CommandRequest request, UserDto userDto, AccountDto accountDto, String lang) {
        request.getCurrentSession().ifPresent(HttpSession::invalidate);
        final HttpSession session = request.createSession();
        session.setAttribute(Constant.USER_ID_NAME, userDto.getId());
        session.setAttribute(Constant.USER_LOGIN_ATTRIB_NAME, userDto.getLogin());
        session.setAttribute(Constant.ACCOUNT_PARAM, accountDto);
        session.setAttribute(Constant.ROLE_ATTRIB_NAME, accountDto.getRole());
        session.setAttribute(Constant.LANG_PARAM, lang);
        return SUCCESS_RESPONSE;
    }

}
