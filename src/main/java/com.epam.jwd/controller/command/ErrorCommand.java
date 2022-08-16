package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.service.exception.ServiceException;

import javax.servlet.http.HttpSession;

public enum ErrorCommand implements Command {
    INSTANCE;

    public static final CommandResponse SHOW_PAGE_CONTEXT = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_MAIN_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        session.setAttribute(Constant.ERROR_PARAM,Constant.ERROR_LESS_ROOTS_MSS);
        return SHOW_PAGE_CONTEXT;
    }
}
