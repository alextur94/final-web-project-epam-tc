package com.epam.jwd.controller.api;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.service.exception.ServiceException;

import java.sql.SQLException;

public interface Command {
    CommandResponse execute(CommandRequest request) throws ServiceException, SQLException;
    static Command of(String name){
        return Commands.getCommand(name);
    }
}
