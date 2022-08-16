package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.impl.AccountConverterImpl;
import com.epam.jwd.service.converter.impl.UserConverterImpl;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import com.epam.jwd.service.validator.impl.AccountValidatorImpl;
import com.epam.jwd.service.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum UpdatePersonCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(UpdatePersonCommand.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_PANEL_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_ADMIN_PANEL_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final UserValidatorImpl userValidatorImpl = new UserValidatorImpl();
    private final AccountValidatorImpl accountValidatorImpl = new AccountValidatorImpl();
    private final UserConverterImpl userConverterImpl = new UserConverterImpl();
    private final AccountConverterImpl accountConverterImpl = new AccountConverterImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        try {
            String userLogin = request.getParameter(Constant.LOGIN_PARAM);
            String userPassword = request.getParameter(Constant.PASSWORD_PARAM);

            Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
            UserDto user = userService.getById(userId);
            //User
            if (!userLogin.equals(user.getLogin())) {
                userService.getByLoginForUpdate(userLogin);
                user.setLogin(userLogin);
            }
            if (!userPassword.isEmpty()){
                if (!userDao.criptPassword(userPassword).equals(user.getPassword())){
                    userValidatorImpl.validatePassword(userPassword);
                    user.setPassword(userDao.criptPassword(userPassword));
                }
            }

            //Account
            AccountDto account = accountService.getById(user.getAccountId());

            String name = request.getParameter(Constant.NAME_PARAM);
            if (!name.isEmpty() && !name.equals(account.getName())) {
                account.setName(name);
            }
            String surname = request.getParameter(Constant.SURNAME_PARAM);
            if (!surname.equals(account.getSurname())) {
                account.setSurname(surname);
            }
            String email = request.getParameter(Constant.EMAIL_PARAM);
            if (!email.equals(account.getEmail())) {
                accountService.getByEmailForUpdate(email);
                account.setEmail(email);
            }
            String phone = request.getParameter(Constant.PHONE_PARAM);
            if (!phone.equals(account.getPhone())) {
                account.setPhone(phone);
            }
            String docId = request.getParameter(Constant.DOCUMENT_ID_PARAM);
            if (!docId.equals(account.getDocumentId())) {
                account.setDocumentId(docId);
            }
            String license = request.getParameter(Constant.DRIVE_LICENSE_NUMBER_PARAM);
            if (!license.equals(account.getDriveLicenseNumber())) {
                account.setDriveLicenseNumber(license);
            }
            String address = request.getParameter(Constant.ADDRESS_PARAM);
            if (!address.equals(account.getAddress())) {
                account.setAddress(address);
            }
            Double balance = Double.parseDouble(request.getParameter(Constant.BALANCE_PARAM));
            if (!balance.equals(account.getBalance())) {
                account.setBalance(balance);
            }
            User convertUser = userConverterImpl.convert(user);
            Account convertAccount = accountConverterImpl.convert(account);
            userDao.updateUserAccount(convertUser, convertAccount);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_SAVE_PERSON_MSS);
            return SUCCESS_RESPONSE;
        } catch (ServiceException | DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
