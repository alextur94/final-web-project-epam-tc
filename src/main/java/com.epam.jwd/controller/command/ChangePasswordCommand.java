package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum ChangePasswordCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

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
    private final UserServiceImpl userService = new UserServiceImpl();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        final Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        final String password = request.getParameter(Constant.PASSWORD_PARAM);
        try {
            try {
                userValidator.validatePassword(password);
            } catch (ServiceException e) {
                logger.error(e);
                session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
                return ERROR_RESPONSE;
            }
            userService.changePassword(userId, password);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_UPDATE_PASS_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
