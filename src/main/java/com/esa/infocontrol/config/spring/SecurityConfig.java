/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.config.spring;

import com.esa.infocontrol.data.jdbc.BaseDataJDBC;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    public PasswordEncoder getPasswordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder;
    }

    public DataSource baseData() {
        System.out.println("Getting security DataSource");
        return BaseDataJDBC.getDataSourceByName("InfoControl");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(baseData())
                .passwordEncoder(getPasswordEncoder())
                .rolePrefix("ROLE_")
                .getUserDetailsService().setEnableGroups(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Configuring InfoControl Security. . . ");
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .portMapper().http(8080).mapsTo(8181)
                .and()
                .requiresChannel()
                .anyRequest().requiresSecure()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasRole("USERAPI")
                .antMatchers("/*").hasRole("USER")
                .and()
                .httpBasic().realmName("InfoControlRestAPI REALM");
    }

}
