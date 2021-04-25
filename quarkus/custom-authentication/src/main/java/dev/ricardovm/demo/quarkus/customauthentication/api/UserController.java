package dev.ricardovm.demo.quarkus.customauthentication.api;

import io.quarkus.security.Authenticated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("user")
public class UserController {

    @GET
    @Authenticated
    public String user() {
        return "user";
    }
}
