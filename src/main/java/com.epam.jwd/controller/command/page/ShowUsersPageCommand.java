package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public enum ShowUsersPageCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowUsersPageCommand.class);

    private final UserServiceImpl userDto = new UserServiceImpl();
    private final AccountServiceImpl accountDto = new AccountServiceImpl();

    private static final CommandResponse SHOW_PAGE_CONTEXT = new CommandResponse(){
        @Override
        public String getPath() {
//            return Constant.PATH_PAGE_USERS;
            return null;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        logger.info("command " + ShowUsersPageCommand.class);
        String findLogin = request.getParameter("login");
        if (findLogin != null) {
            UserDto user = userDto.getByLogin(findLogin);
            AccountDto account = accountDto.getById(user.getAccountId());
            request.setAttribute("userLogin", user);
            request.setAttribute("accountLogin", account);
        } else {
            List<UserDto> users = userDto.getAll();
            List<AccountDto> accounts = accountDto.getAll();
            request.setAttribute("userList", users);
            request.setAttribute("accountList", accounts);
        }
        return SHOW_PAGE_CONTEXT;
    }
}
