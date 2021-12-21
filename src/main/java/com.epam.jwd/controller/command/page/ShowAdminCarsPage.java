package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public enum ShowAdminCarsPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowAdminCarsPage.class);

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
            return Constant.ERROR_PARAM;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Integer userId = Integer.parseInt(request.getParameter(Constant.USER_ID_NAME));
        Integer statusId = Integer.parseInt(request.getParameter(Constant.ORDER_STATUS_PARAM));
        try {
            List<CarDto> listCar = carService.getAll();
            List<OrderDto> listOrder;
            listOrder = orderService.getByStatus(statusId);
            request.setAttribute("orderList", listOrder);
            request.setAttribute("carList", listCar);
            request.setAttribute("userId", userId);
            return SUCCESS_RESPONSE;
        } catch (DaoException e) {
            logger.error(e);
            session.setAttribute(Constant.ERROR_PARAM, e.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
