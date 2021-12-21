package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.PriceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum ShowAutoCardPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowAutoCardPage.class);
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
            return Constant.PATH_PAGE_AUTO_CARD;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    final CarServiceImpl carService = new CarServiceImpl();
    final PriceServiceImpl priceService = new PriceServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        try {
            Integer carId = Integer.parseInt(request.getParameter(Constant.CAR_ID_PARAM));
            PriceDto price = priceService.getById(carId);
            CarDto car = carService.getById(carId);
            request.setAttribute("price", price);
            request.setAttribute("car", car);
            request.setAttribute("carId", carId);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
