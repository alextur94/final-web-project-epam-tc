package com.epam.jwd.controller.command.page;

import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.PageConstant;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public enum ShowUsersPageCommand implements Command {
    INSTANCE;
    private static final Logger logger = LogManager.getLogger(ShowUsersPageCommand.class);

    private final UserDaoImpl userDao = new UserDaoImpl();

    private static final CommandResponse SHOW_PAGE_CONTEXT = new CommandResponse(){
        @Override
        public String getPath() {
            return PageConstant.PATH_PAGE_USERS;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        logger.info("command " + ShowUsersPageCommand.class);
        List<User> userDto = userDao.findAll();
        request.setAttribute("users", userDto);
        return SHOW_PAGE_CONTEXT;
    }
}
