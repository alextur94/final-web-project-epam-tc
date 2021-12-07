package com.epam.jwd.controller.filter;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.dao.model.account.Role;
import static com.epam.jwd.dao.model.account.Role.UNKNOWN;
import static com.epam.jwd.controller.Controller.COMMAND_PARAM;
import static com.epam.jwd.controller.command.page.LoginCommand.USER_ROLE_ATTRIB_NAME;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    private static final String ERROR_PAGE_LOCATION = "/controller?command=error";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
