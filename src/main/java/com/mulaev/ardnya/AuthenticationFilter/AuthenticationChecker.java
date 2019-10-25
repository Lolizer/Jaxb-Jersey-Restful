package com.mulaev.ardnya.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/*
 * NameBinded authorization checker class
 */
@Provider
@CheckedSource
public class AuthenticationChecker implements ContainerRequestFilter {
    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext ctx) {
        String token = (String) httpServletRequest.getSession().getAttribute("Authentication");
        String user = (String) httpServletRequest.getSession().getAttribute("User");

        if (token == null || token.equals("") ||
                user == null || user.equals("") || !token.endsWith(user))
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                            entity("You have to authenticate!").build());
    }
}
