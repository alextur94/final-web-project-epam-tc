package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.page.*;
import com.epam.jwd.dao.model.account.Role;

import java.util.Arrays;
import java.util.List;

public enum Commands {

    LOCALE(LocaleCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    LOGIN(LoginCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    LOGOUT(LogoutCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    REGISTRATION(RegistrationCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CANCEL_ORDER(CancelOrderCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CHANGE_PASSWORD(ChangePasswordCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CHANGE_BALANCE(СhangeBalanceCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    NEW_ORDER(NewOrderCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    PAY(PayCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    SAVE_ACCOUNT(SaveAccountCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    NEW_CAR(NewCarCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    APPROVE_CAR(ApproveCarCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CANCEL_ORDER_ADMIN(CancelOrderAdminCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    BEGIN_RENT(BrginRentCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    END_RENT(EndRentCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),

    LOGIN_PAGE(ShowLoginPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    REGISTRATION_PAGE(ShowRegistrationPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    MAIN_PAGE(ShowMainPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CARS_PAGE(ShowCarsPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    AUTO_CARD_PAGE(ShowAutoCardPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ORDER_PANEL_PAGE(ShowOrderPanelPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    USER_PANEL_PAGE(ShowUserPanelPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    USER_ORDERS_PAGE(ShowUserOrdersPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ADMIN_ORDERS_PAGE(ShowAdminOrdersPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ADMIN_ACTIVE_ORDERS_PAGE(ShowAdminActiveOrdersPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ADMIN_READY_ORDERS_PAGE(ShowAdminReadyOrdersPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ADMIN_CLOSE_ORDERS_PAGE(ShowAdminCloseOrdersPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ADMIN_NEW_CAR_PAGE(ShowAdminNewCarPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN);

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
                .orElse(ShowMainPage.INSTANCE);
    }

    public static Commands getCommands(String commandName) {
        for (Commands command : values()) {
            if (command.name().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return MAIN_PAGE;
    }
}
