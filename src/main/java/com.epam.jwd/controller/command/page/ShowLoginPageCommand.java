package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ShowLoginPageCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ShowLoginPageCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request) {
        return SHOW_PAGE_CONTEXT;
    }

    private static final CommandResponse SHOW_PAGE_CONTEXT = new CommandResponse(){
        @Override
        public String getPath() {
            return Constant.PATH_PAGE_LOGIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
}
