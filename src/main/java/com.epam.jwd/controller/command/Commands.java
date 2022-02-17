package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.command.page.*;
import com.epam.jwd.dao.model.account.Role;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    ERROR(ErrorCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    LOCALE(LocaleCommand.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    LOGIN(LoginCommand.INSTANCE, Role.UNKNOWN),
    LOGOUT(LogoutCommand.INSTANCE, Role.USER, Role.ADMIN),
    REGISTRATION(RegistrationCommand.INSTANCE, Role.UNKNOWN),
    CANCEL_ORDER(CancelOrderCommand.INSTANCE, Role.USER, Role.ADMIN),
    CHANGE_PASSWORD(ChangePasswordCommand.INSTANCE, Role.USER, Role.ADMIN),
    CHANGE_BALANCE(СhangeBalanceCommand.INSTANCE, Role.USER, Role.ADMIN),
    NEW_ORDER(NewOrderCommand.INSTANCE, Role.USER),
    PAY(PayCommand.INSTANCE, Role.USER),
    SAVE_ACCOUNT(SaveAccountCommand.INSTANCE, Role.USER, Role.ADMIN),
    NEW_CAR(NewCarCommand.INSTANCE, Role.ADMIN),
    APPROVE_CAR(ApproveCarCommand.INSTANCE, Role.ADMIN),
    CANCEL_ORDER_ADMIN(CancelOrderAdminCommand.INSTANCE, Role.ADMIN),
    BEGIN_RENT(BrginRentCommand.INSTANCE, Role.ADMIN),
    END_RENT(EndRentCommand.INSTANCE, Role.ADMIN),
    UPDATE_PERSON(UpdatePersonCommand.INSTANCE, Role.ADMIN),

    LOGIN_PAGE(ShowLoginPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    REGISTRATION_PAGE(ShowRegistrationPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    MAIN_PAGE(ShowMainPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    CARS_PAGE(ShowCarsPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    AUTO_CARD_PAGE(ShowAutoCardPage.INSTANCE, Role.UNKNOWN, Role.USER, Role.ADMIN),
    ORDER_PANEL_PAGE(ShowOrderPanelPage.INSTANCE, Role.USER),
    USER_PANEL_PAGE(ShowUserPanelPage.INSTANCE, Role.USER),
    USER_ORDERS_PAGE(ShowUserOrdersPage.INSTANCE, Role.USER),
    ADMIN_PANEL_PAGE(ShowAdminPanelPage.INSTANCE, Role.ADMIN),
    ADMIN_ORDERS_PAGE(ShowAdminOrdersPage.INSTANCE, Role.ADMIN),
    ADMIN_ACTIVE_ORDERS_PAGE(ShowAdminActiveOrdersPage.INSTANCE, Role.ADMIN),
    ADMIN_READY_ORDERS_PAGE(ShowAdminReadyOrdersPage.INSTANCE, Role.ADMIN),
    ADMIN_WAITING_ORDERS_PAGE(ShowAdminWaitingOrdersPage.INSTANCE, Role.ADMIN),
    EDIT_USER_PAGE(ShowEditUserPage.INSTANCE, Role.ADMIN),
    ADMIN_NEW_CAR_PAGE(ShowAdminNewCarPage.INSTANCE, Role.ADMIN);

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
