package com.mulaev.ardnya.App.services;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

/*
 * the authentication service endpoint
 */
@Path("authentication")
public class AuthenticationEndpoint {
    @Context
    private HttpServletRequest httpServletRequest;

    /*
     * gets non-encoded params via GET http requests
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticateUserWithGet(@NotNull @QueryParam("username") String username,
                                            @NotNull @QueryParam("password") String password) {
        if (username == null || password == null || username.equals("") || password.equals(""))
            return Response.status(Response.Status.FORBIDDEN).build();

        return Response.ok(authenticate(username, password)).build();
    }

    /*
     * gets encoded params via POST http requests
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticateUser(@NotNull @FormParam("username") String username,
                                     @NotNull @FormParam("password") String password) {
        if (username == null || password == null || username.equals("") || password.equals(""))
            return Response.status(Response.Status.FORBIDDEN).build();

        return Response.ok(authenticate(username, password)).build();
    }

    /*
     * stores user & token as the session's attributes
     */
    private String authenticate(String username, String password) {
        String token;

        httpServletRequest.getSession().setAttribute("Authentication", token = issueToken(username));
        httpServletRequest.getSession().setAttribute("User", username);

        return token;
    }

    /*
     * produces a String session token
     */
    private String issueToken(String username) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int tokenLength = 256;
        SecureRandom random = new SecureRandom();
        char[] symbols = characters.toCharArray();
        char[] buff = new char[tokenLength];

        for (int idx = 0; idx < buff.length; ++idx)
            buff[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buff).substring(0, 256 - username.length()) + username;
    }
}
