package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public enum ShowAdminReadyOrdersPage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowAdminReadyOrdersPage.class);

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ADMIN_READY_ORDERS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ADMIN_READY_ORDERS;
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
        try {
            Integer countRowListNew = orderService.getCountRowByStatus((int) Status.NEW.getId());
            Integer countRowListActive = orderService.getCountRowByStatus((int) Status.ACTIVE.getId());
            Integer countRowListClose = orderService.getCountRowByStatus((int) Status.CLOSE.getId());
            Integer[] countRowInLists = {countRowListNew, countRowListActive, countRowListClose};
            List<CarDto> listCar = carService.getAll();
            List<OrderDto> listOrderReady = orderService.getByStatus((int) Status.READY.getId());
            Map<Integer, AccountDto> personMap = orderService.testList((int) Status.READY.getId());
            session.setAttribute("orderList", listOrderReady);
            session.setAttribute("personMap", personMap);
            session.setAttribute("carList", listCar);
            session.setAttribute("countRow", countRowInLists);
            return SUCCESS_RESPONSE;
        } catch (DaoException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
