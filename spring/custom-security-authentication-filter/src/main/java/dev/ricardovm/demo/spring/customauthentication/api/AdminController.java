package dev.ricardovm.demo.spring.customauthentication.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    @GetMapping
    public String admin() {
        return "admin";
    }
}
