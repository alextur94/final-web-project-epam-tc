package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public enum ShowAdminNewCarPage implements Command {
    INSTANCE;

    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_ADMIN_NEW_CAR;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        return SUCCESS_RESPONSE;
    }
}
