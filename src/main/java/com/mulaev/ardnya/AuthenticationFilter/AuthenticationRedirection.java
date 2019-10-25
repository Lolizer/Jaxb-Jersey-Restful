package com.mulaev.ardnya.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;

/*
 * performs redirecting operations if the session is unknown
 */
@Provider
@PreMatching
public class AuthenticationRedirection implements ContainerRequestFilter {
    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        String token = (String) httpServletRequest.getSession().
                getAttribute("Authentication");
        String user = (String) httpServletRequest.getSession().
                getAttribute("User");

        if (!shouldRedirect(reqContext))
            return;

        if (token == null || token.equals("") || user == null ||
                user.equals("") || !token.endsWith(user))
            reqContext.setRequestUri(
                URI.create(reqContext.getUriInfo().getRequestUri().toString() +
                        "/unauthorized"));
    }

    //Conditional redirection workaround
    private boolean shouldRedirect(ContainerRequestContext reqContext) {
        UriInfo uriInfo = reqContext.getUriInfo();
        String path = uriInfo.getRequestUri().getPath();

        if (path.endsWith("/users/all"))
            return true;

        return false;
    }
}
