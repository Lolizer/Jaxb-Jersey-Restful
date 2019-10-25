package com.mulaev.ardnya.App.net;

import com.mulaev.ardnya.AuthenticationFilter.CheckedSource;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class MyApplication{
    @Context
    private ServletContext application;

    /*
    @Path("status")
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public String test() {
        return (String) application.getAttribute("status");
    }
    */

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String response() {
        return "Still work!";
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @CheckedSource
    public String response2() {

        return "Within hello/test";
    }

    @Path("/getXml")
    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public String test2() {
        return (String) application.getAttribute("Status") + "Error!";
    }

    @Path("/getXml2")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String test3() {
        return "Error!";
    }
}