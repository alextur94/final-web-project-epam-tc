package com.epam.jwd.controller.api;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.service.exception.ServiceException;

public interface Command {
    CommandResponse execute(CommandRequest request) throws ServiceException;
    static Command of(String name){
        return Commands.getCommand(name);
    }
}
