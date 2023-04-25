package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;

import javax.servlet.http.HttpSession;

public enum LocaleCommand implements Command {
    INSTANCE;

    private static final CommandResponse LOCALE_SUCCESS_RESPONSE = new CommandResponse() {
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
        final String lang = request.getParameter(Constant.LANG_PARAM);
        HttpSession session = request.getCurrentSession().get();
        session.setAttribute(Constant.LANG_PARAM, lang);
        return LOCALE_SUCCESS_RESPONSE;
    }
}
