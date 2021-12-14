package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.page.*;
import com.epam.jwd.dao.model.account.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    TEST(Test.INSTANCE),

    SHOW_MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    SHOW_CARS_PAGE(ShowCarsPageCommand.INSTANCE),
    SHOW_CONTACTS_PAGE(null),
    SHOW_ABOUT_US_PAGE(null),
    SHOW_LOGIN_PAGE(ShowLoginPageCommand.INSTANCE),
    SHOW_REGISTRATION_PAGE(ShowRegistrationPageCommand.INSTANCE),
    AUTO_CARD_PAGE(ShowAutoCardPageCommand.INSTANCE),

    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    REGISTRATION(RegistrationCommand.INSTANCE),
    NEW_CAR(NewCarCommand.INSTANCE, Role.ADMIN),
    NEW_ORDER(NewOrderCommand.INSTANCE),
    SAVE_ACCOUNT(SaveAccountCommand.INSTANCE),
    CHANGE_PASSWORD(PasswordCommand.INSTANCE),
    ADD_BALANCE(AddBalanceCommand.INSTANCE),

    ADMIN_PANEL_PAGE(ShowAdminPanelPage.INSTANCE, Role.ADMIN),
    USER_PANEL_PAGE(ShowUserPanelPage.INSTANCE, Role.USER),
    USER_ORDERS_PAGE(ShowUserOrdersPage.INSTANCE, Role.USER),
    ORDER_PAGE(ShowOrderPanelPage.INSTANCE, Role.USER, Role.ADMIN),

    USERS(ShowUsersPageCommand.INSTANCE);//admin

    private static final Logger logger = LogManager.getLogger(Commands.class);

    private final Command command;
    private final List<Role> allowedRoles;

    Commands(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public List<Role> getAllowedRoles(){
        return allowedRoles;
    }

    public static Command getCommand(String name){
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(ShowMainPageCommand.INSTANCE);
    }

    public static Commands getCommands(String commandName) {
        for (Commands command : values()) {
            if (command.name().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return SHOW_MAIN_PAGE;
    }
}
