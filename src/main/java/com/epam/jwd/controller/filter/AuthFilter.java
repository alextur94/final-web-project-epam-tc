package com.epam.jwd.controller.filter;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.controller.command.Constant;
import com.epam.jwd.dao.model.account.Role;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;


@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final String ERROR_PAGE_LOCATION = "/controller?command=error";
    private final Map<Role, Set<Commands>> commandsByRoles;

    public AuthFilter() {commandsByRoles = new EnumMap<>(Role.class); }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        for (Commands command : Commands.values()) {
            for (Role allowedRole : command.getAllowedRoles()) {
                Set<Commands> commands = commandsByRoles.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(Commands.class));
                commands.add(command);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final Commands command = Commands.getCommands(req.getParameter(Constant.COMMAND_PARAM));
        final HttpSession session = req.getSession(false);
        Role currentRole = extractRoleFromSession(session);
        final Set<Commands> allowedCommands = commandsByRoles.get(currentRole);
        if (allowedCommands.contains(command)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(ERROR_PAGE_LOCATION);
        }
    }

    private Role extractRoleFromSession(HttpSession session) {
        return session != null && session.getAttribute(Constant.ROLE_ATTRIB_NAME) != null
                ? (Role) session.getAttribute(Constant.ROLE_ATTRIB_NAME)
                : Role.UNKNOWN;
    }
}
