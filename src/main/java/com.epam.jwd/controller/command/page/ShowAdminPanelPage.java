package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public enum ShowAdminPanelPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowAdminPanelPage.class);

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ADMIN_PANEL;
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

    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        try {
            Integer countRow = orderService.getCountRowByStatus((int) Status.NEW.getId());
            String userIdString = request.getParameter(Constant.USER_ID_NAME);
            if (userIdString != null){
                Integer userId = Integer.parseInt(userIdString);
                UserDto userDto = userService.getById(userId);
                AccountDto accountDto = accountService.getById(userDto.getAccountId());
                session.setAttribute("infoUserById", userDto);
                session.setAttribute("infoAccountById", accountDto);
            }
            session.setAttribute("countRow", countRow);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
