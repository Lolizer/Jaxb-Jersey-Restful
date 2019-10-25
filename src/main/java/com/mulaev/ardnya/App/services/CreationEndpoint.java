package com.mulaev.ardnya.App.services;

import com.mulaev.ardnya.AppElements.User;
import com.mulaev.ardnya.AuthenticationFilter.CheckedSource;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Statement;

/*
 * user creation endpoint class
 */
@Path("create")
@CheckedSource
public class CreationEndpoint {
    @Context
    private ServletContext application;

    private Connection connection;
    private Statement statement;

    /*
     * handles GET http request using params
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User createUserGet(@NotNull @QueryParam("name") String name,
                              @NotNull @QueryParam("surname") String surname,
                              @NotNull @QueryParam("birthday") String birthday,
                              @NotNull @QueryParam("address") String address) {

        storeUser(name, surname, birthday, address);

        return new User(name,surname,birthday,address);
    }

    /*
     * handles POST http request using form params
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public User createUserPost(@NotNull @FormParam("name") String name,
                               @NotNull @FormParam("surname") String surname,
                               @NotNull @FormParam("birthday") String birthday,
                               @NotNull @FormParam("address") String address) {

        storeUser(name, surname, birthday, address);

        return new User(name,surname,birthday,address);
    }

    /*
     * handles POST http requests passing User object in
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public User useUserToCreateUser(User user) {

        storeUser(user.getFirstName(), user.getSurName(), user.getBirthday(), user.getAddress());

        return user;
    }

    /*
     * performs actual storing to database
     */
    private void storeUser(String name, String surname, String birthday, String address) {
        String sql;
        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format(
                    "INSERT INTO users (firstname,surname,birthday,address) " +
                    "VALUES ('%s','%s','%s','%s');",
                    name, surname, birthday, address);

            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
