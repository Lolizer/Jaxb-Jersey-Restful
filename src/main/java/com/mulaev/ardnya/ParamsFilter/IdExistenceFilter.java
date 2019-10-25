package com.mulaev.ardnya.ParamsFilter;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * guarantees existence of id param gained
 */
@Provider
@UserExistenceEnsured
public class IdExistenceFilter implements ContainerRequestFilter {
    @Context
    private ServletContext application;

    private Connection connection;
    private Statement statement;

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        MultivaluedMap<String, String> pathParameters =
                reqContext.getUriInfo().getPathParameters();
        String sql;

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format("SELECT * FROM users WHERE id=%s",
                                pathParameters.get("id").get(0));

            ResultSet rs = statement.executeQuery(sql);

            if (!rs.next())
                reqContext.abortWith(Response.status(Response.Status.BAD_REQUEST).
                                 entity("There isn't that id!").build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
