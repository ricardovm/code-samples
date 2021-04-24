package dev.ricardovm.demo.spring.customauthentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new CustomFilter(), AnonymousAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/hello").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }

    //UserDetailsService (quando você armazena a senha)
    //AuthenticationProvider (quando você não armazena a senha)
}
