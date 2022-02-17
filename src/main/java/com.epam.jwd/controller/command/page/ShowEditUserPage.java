package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public enum ShowEditUserPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowEditUserPage.class);

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_EDIT_USER;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ADMIN_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final UserServiceImpl userService = new UserServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserConverter userConverter = new UserConverter();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer id;
        UserDto userDto;
        AccountDto accountDto;
        try {
            String userId = request.getParameter(Constant.USER_ID_NAME);
            String userLogin = request.getParameter(Constant.LOGIN_PARAM);
            if (!userId.isEmpty()) {
                id = Integer.parseInt(userId);
                userDto = userService.getById(id);
                userDto.setPassword("");
                accountDto = accountService.getById(userDto.getAccountId());
            }
            else if (!userLogin.isEmpty()) {
                userDto = userConverter.convert(userService.getByLogin(userLogin));
                userDto.setPassword("");
                accountDto = accountService.getById(userDto.getAccountId());
            } else {
                logger.error(Constant.ERROR_FOUND_CANT_BE_NULL_MSS);
                session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_FOUND_CANT_BE_NULL_MSS);
                return ERROR_RESPONSE;
            }
            request.setAttribute("user", userDto);
            request.setAttribute("account", accountDto);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
