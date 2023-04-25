package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;

import javax.servlet.http.HttpSession;
import java.util.Collections;

public enum LogoutCommand implements Command {
    INSTANCE;

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

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        Collections.list(session.getAttributeNames())
                .forEach(session::removeAttribute);
        session.invalidate();
        return SHOW_PAGE_CONTEXT;
    }
}
