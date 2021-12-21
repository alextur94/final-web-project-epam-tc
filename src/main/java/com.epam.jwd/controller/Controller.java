package com.epam.jwd.controller;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String commandName = req.getParameter(Constant.COMMAND_PARAM);
        final Command command = Command.of(commandName);
        CommandResponse response = null;
        try {
            response = command.execute(new CommandRequest() {
                @Override
                public HttpSession createSession() {
                    return req.getSession(true);
                }

                @Override
                public Optional<HttpSession> getCurrentSession() {
                    return Optional.ofNullable(req.getSession(false));
                }

                @Override
                public void invalidateCurrentSession() {
                    final HttpSession session = req.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                }

                @Override
                public String getParameter(String name) {
                    return req.getParameter(name);
                }

                @Override
                public void setAttribute(String name, Object value) {
                    req.setAttribute(name, value);
                }
            });
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (response.isRedirect()) {
            resp.sendRedirect(response.getPath());
        } else {
            final RequestDispatcher dispatcher = req.getRequestDispatcher(response.getPath());
            dispatcher.forward(req, resp);
        }
    }
}
