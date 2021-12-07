package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.PageConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ShowMainPageCommand implements Command {
    INSTANCE;
    private static final Logger logger = LogManager.getLogger(ShowMainPageCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request) {
        return DEFAULT_PAGE_CONTEXT;
    }

    private static final CommandResponse DEFAULT_PAGE_CONTEXT = new CommandResponse() {
        @Override
        public String getPath() {
            return PageConstant.PATH_PAGE_MAIN;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
}
