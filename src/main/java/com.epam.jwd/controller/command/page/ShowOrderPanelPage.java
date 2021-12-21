package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum ShowOrderPanelPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowOrderPanelPage.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_MAIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ORDER_PANEL;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final CarServiceImpl carService = new CarServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        Integer carId = Integer.parseInt(request.getParameter(Constant.CAR_ID_PARAM));
        Double priceDay = Double.parseDouble(request.getParameter(Constant.PRICE_PARAM));
        try {
            accountService.getById(userService.getById(userId).getAccountId());
            CarDto carDto = carService.getById(carId);
            request.setAttribute("car", carDto);
            request.setAttribute("priceDay", priceDay);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
