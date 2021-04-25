package dev.ricardovm.demo.quarkus.customauthentication.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminController {

    @GET
    public String admin() {
        return "admin";
    }
}
