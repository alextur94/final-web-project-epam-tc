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
            String name = request.getParameter(Constant.NAME_PARAM);
            String surname = request.getParameter(Constant.SURNAME_PARAM);
            String phone = request.getParameter(Constant.PHONE_PARAM);
            String docId = request.getParameter(Constant.DOCUMENT_ID_PARAM);
            String license = request.getParameter(Constant.DRIVE_LICENSE_NUMBER_PARAM);
            String address = request.getParameter(Constant.ADDRESS_PARAM);
            String[] params = {name, surname, phone, docId, license, address};
            for (String param : params) {
                if (accountService.checkNotNull(param)){
                    logger.error(Constant.FIELDS_NOT_BE_NULL_MSS);
                    session.setAttribute(Constant.ERROR_PARAM, Constant.FIELDS_NOT_BE_NULL_MSS);
                    return ERROR_RESPONSE;
                }
            }
            AccountDto accountDto = accountService.getById(accountId);
            accountDto.setName(name);
            accountDto.setSurname(surname);
            accountDto.setPhone(phone);
            accountDto.setDocumentId(docId);
            accountDto.setDriveLicenseNumber(license);
            accountDto.setAddress(address);
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
