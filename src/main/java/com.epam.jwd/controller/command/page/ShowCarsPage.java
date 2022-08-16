package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.dao.impl.PriceDaoImpl;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShowCarsPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowCarsPage.class);

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_CARS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse ERROR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_CARS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_CARS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final CarServiceImpl carService = new CarServiceImpl();
    private final CarDaoImpl carDao = new CarDaoImpl();
    private final PriceDaoImpl priceDao = new PriceDaoImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        String tempPage = request.getParameter(Constant.PAGE_PARAM);
        Integer page = 1;
        if (tempPage != null) page = Integer.parseInt(tempPage);
        try {
            Integer size = 12;
            Integer skip = (page - 1) * size;
            Integer countCars = carService.getCountRowFromCars();
            Integer countPages = (int) Math.ceil(countCars * 1.0 / size);
            if (page > countPages || page < 1) {
                logger.error(Constant.ERROR_PAGINATION_PAGE_MSS);
                return ERROR_PAGE_RESPONSE;
            }
            List<Car> cars = carDao.findByRange(skip, size);
            Map<Integer, Double> prices = new HashMap<>();
            for (Car car : cars) {
                Price price = priceDao.findById(car.getPriceId());
                prices.put(price.getId(), price.getPricePerDay());
            }
            session.setAttribute("cars", cars);
            session.setAttribute("price", prices);
            request.setAttribute("currentPage", page);
            request.setAttribute("pages", countPages);
            return SUCCESS_RESPONSE;
        } catch (ServiceException | DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
