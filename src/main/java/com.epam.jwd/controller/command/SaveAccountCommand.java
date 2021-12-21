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

public enum SaveAccountCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(SaveAccountCommand.class);

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

    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        try {
            Integer accountId = userService.getById(userId).getAccountId();
            AccountDto accountDto = accountService.getById(accountId);
            accountDto.setName(request.getParameter(Constant.NAME_PARAM));
            accountDto.setSurname(request.getParameter(Constant.SURNAME_PARAM));
            accountDto.setPhone(request.getParameter(Constant.PHONE_PARAM));
            accountDto.setDocumentId(request.getParameter(Constant.DOCUMENT_ID_PARAM));
            accountDto.setDriveLicenseNumber(request.getParameter(Constant.DRIVE_LICENSE_NUMBER_PARAM));
            accountDto.setAddress(request.getParameter(Constant.ADDRESS_PARAM));
            accountDto.setStatus(1);
            accountService.update(accountDto);
            session.setAttribute(Constant.ACCOUNT_PARAM, accountDto);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_SAVE_INFO_ACCOUNT_MSS);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
