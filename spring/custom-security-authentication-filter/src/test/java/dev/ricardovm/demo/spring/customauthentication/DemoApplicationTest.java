package dev.ricardovm.demo.spring.customauthentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void hello_endpoint_should_work_without_authentication() {
        var result = this.restTemplate.getForEntity("http://localhost:" + port + "/hello", String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("hello", result.getBody());
    }

    @Test
    void admin_endpoint_should_NOT_work_without_authentication() {
        var result = this.restTemplate.getForEntity("http://localhost:" + port + "/admin", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    void admin_endpoint_should_work_with_authentication_backdoor_header() {
        var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.TEXT_PLAIN));
        headers.add("backdoor", "true");

        var result = restTemplate.exchange("http://localhost:" + port + "/admin", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("admin", result.getBody());
    }

    @Test
    void admin_endpoint_should_NOT_work_with_regular_authentication() {
        var username = securityProperties.getUser().getName();
        var password = securityProperties.getUser().getPassword();

        var result = this.restTemplate
                .withBasicAuth(username, password)
                .getForEntity("http://localhost:" + port + "/admin", String.class);

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void user_endpoint_should_NOT_work_without_authentication() {
        var result = this.restTemplate.getForEntity("http://localhost:" + port + "/user", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    void user_endpoint_should_work_with_regular_authentication() {
        var username = securityProperties.getUser().getName();
        var password = securityProperties.getUser().getPassword();

        var result = this.restTemplate
                .withBasicAuth(username, password)
                .getForEntity("http://localhost:" + port + "/user", String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("user", result.getBody());
    }
}