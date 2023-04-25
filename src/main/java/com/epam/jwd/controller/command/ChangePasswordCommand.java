package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import com.epam.jwd.service.validator.impl.UserValidatorImpl;
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
    private final UserValidatorImpl userValidatorImpl = new UserValidatorImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        final Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        final String passwordOld = request.getParameter(Constant.PASSWORD_OLD_PARAM);
        final String password = request.getParameter(Constant.PASSWORD_PARAM);
        final String passwordRepeat = request.getParameter(Constant.PASSWORD_REPEAT_PARAM);
        try {
            try {
                if (!userService.checkPasswordOnCorrect(userId, passwordOld)) {
                    logger.error(Constant.ERROR_FAILED_PASS_MSS);
                    session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_FAILED_PASS_MSS);
                    return ERROR_RESPONSE;
                }
                userValidatorImpl.validatePassword(password);
                userValidatorImpl.validatePassword(passwordRepeat);
                userValidatorImpl.validateRepeatPassword(password, passwordRepeat);
            } catch (ServiceException e) {
                logger.error(e);
                session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
                return ERROR_RESPONSE;
            }
            userService.changePassword(userId, password);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_UPDATE_PASS_MSS);
            return SUCCESS_RESPONSE;
        } catch (ServiceException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
