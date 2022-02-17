package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.OrderDaoImpl;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public enum ShowUserOrdersPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowUserOrdersPage.class);

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_USER_ORDERS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_USER_ORDERS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse ERROR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_USER_ORDERS_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final CarServiceImpl carService = new CarServiceImpl();
    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final OrderDaoImpl orderDao = new OrderDaoImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer userId = (Integer) session.getAttribute(Constant.USER_ID_NAME);
        Integer size = 10;
        Integer page = 1;
        String pageTemp = request.getParameter(Constant.PAGE_PARAM);
        if (pageTemp != null) {
            page = Integer.parseInt(pageTemp);
        }
        try {
            List<CarDto> listCar = carService.getAll();
            Integer start = (page - 1) * size;
            Integer countOrders = orderDao.countOrdersByUser(userId);
            List<OrderDto> listOrder = orderService.findCountOrders(start, size, userId);
            int countPages = (int) Math.ceil(countOrders * 1.0 / size);
            if (page > countPages || page < 1) {
                logger.error(Constant.ERROR_PAGINATION_PAGE_MSS);
                return ERROR_PAGE_RESPONSE;
            }
            request.setAttribute("orderList", listOrder);
            request.setAttribute("carList", listCar);
            request.setAttribute("userId", userId);
            request.setAttribute("pages", countPages);
            request.setAttribute("currentPage", page);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
