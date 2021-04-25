package dev.ricardovm.demo.quarkus.customauthentication;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthenticationTest {

    @Test
    void hello_endpoint_should_work_without_authentication() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(HttpStatus.SC_OK)
             .body(is("hello"));
    }

    @Test
    void admin_endpoint_should_NOT_work_without_authentication() {
        given()
          .when().get("/admin")
          .then()
             .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void admin_endpoint_should_work_with_authentication_backdoor_header() {
        given()
          .header("backdoor", "true")
          .when().get("/admin")
             .then()
             .statusCode(HttpStatus.SC_OK)
             .body(is("admin"));
    }

    @Test
    void admin_endpoint_should_NOT_work_with_regular_authentication() {
        var username = "user";
        var password = "user123";

        given()
          .auth()
             .basic(username, password)
          .when().get("/admin")
             .then()
             .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void user_endpoint_should_NOT_work_without_authentication() {
        given()
          .when().get("/user")
          .then()
             .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void user_endpoint_should_work_with_regular_authentication() {
        var username = "user";
        var password = "user123";

        given()
          .auth()
             .basic(username, password)
          .when().get("/user")
             .then()
             .statusCode(HttpStatus.SC_OK)
             .body(is("user"));
    }

}