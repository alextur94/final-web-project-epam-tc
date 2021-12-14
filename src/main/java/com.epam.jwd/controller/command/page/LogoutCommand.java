package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Collections;

public enum LogoutCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        HttpSession session = request.getCurrentSession().get();
        Collections.list(session.getAttributeNames())
                .forEach(session::removeAttribute);
        return SHOW_PAGE_CONTEXT;

    }
    public static final CommandResponse SHOW_PAGE_CONTEXT = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_INDEX;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
}
