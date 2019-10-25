package com.mulaev.ardnya.App.services;

import com.mulaev.ardnya.AppElements.User;
import com.mulaev.ardnya.AuthenticationFilter.CheckedSource;
import com.mulaev.ardnya.ParamsFilter.UserExistenceEnsured;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Statement;

/*
 * a user info alteration endpoint. The info is changed via @PathParam id
 */
@Path("user_info/{id}")
@CheckedSource
@UserExistenceEnsured
public class AlterationEndpoint {
    @Context
    private ServletContext application;

    /*
     * changes a user info using GET http request
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User changeUserInfo(@NotNull @PathParam(value = "id") String id,
                               @NotNull @QueryParam("name") String name,
                               @NotNull @QueryParam("surname") String surname,
                               @NotNull @QueryParam("birthday") String birthday,
                               @NotNull @QueryParam("address") String address) {

        update(id, name, surname, birthday, address);

        return new User(name, surname, birthday, address);
    }

    /*
     * changes a user info using POST http request and User param
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public User changeUserInfo(@NotNull @PathParam(value = "id") String id,
                               User user) {
        update(id, user.getFirstName(), user.getSurName(),
               user.getBirthday(), user.getAddress());

        return user;
    }

    private void update(String id, String name, String surname,
                        String birthday, String address) {
        String sql;
        Connection connection;
        Statement statement;

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format("UPDATE users set firstName = '%s'," +
                                " surname = '%s', birthday = '%s', address = '%s' WHERE id = '%s'",
                                name, surname, birthday, address, id);
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
