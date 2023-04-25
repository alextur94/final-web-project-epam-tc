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

public enum ShowUserPanelPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowUserPanelPage.class);

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_USER_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_USER_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final UserServiceImpl userService = new UserServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        try {
            UserDto userDto = userService.getById(userId);
            AccountDto accountDto = accountService.getById(userDto.getAccountId());
            request.setAttribute("userName", userDto);
            request.setAttribute("account", accountDto);
            String success = request.getParameter(Constant.SUCCESS_PARAM);
            request.setAttribute(Constant.SUCCESS_PARAM, success);
            return SUCCESS_RESPONSE;
        } catch (ServiceException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
