package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.page.LoginCommand;
import com.epam.jwd.controller.command.page.LogoutCommand;
import com.epam.jwd.controller.command.page.ShowLoginPageCommand;
import com.epam.jwd.controller.command.page.ShowMainPageCommand;
import com.epam.jwd.controller.command.page.ShowUsersPageCommand;

import java.util.Arrays;

public enum Commands {
    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    SHOW_LOGIN(ShowLoginPageCommand.INSTANCE),
    SHOW_USERS(ShowUsersPageCommand.INSTANCE);

    private final Command command;

    Commands(Command command) {
        this.command = command;
    }

    public static Command getCommand(String name){
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(ShowMainPageCommand.INSTANCE);
    }
}
