package com.auditsphere.auditspherebackend.config;


import com.auditsphere.auditspherebackend.jwt.JwtAuthenticationFilter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.security.web.AuthenticationEntryPoint;

import org.springframework.security.web.access.AccessDeniedHandler;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {



    private final JwtAuthenticationFilter jwtAuthenticationFilter;



    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ){

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

    }







    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }








    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        http



                // Disable CSRF because JWT is stateless

                .csrf(csrf ->
                        csrf.disable()
                )



                // Use CorsConfig.java bean

                .cors(cors -> {})





                // No session storage

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )

                )





                .authorizeHttpRequests(auth -> auth



                        // Login/Register public

                        .requestMatchers(
                                "/auth/**"
                        )
                        .permitAll()





                        // Swagger public

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()





                        // Everything else requires JWT

                        .anyRequest()
                        .authenticated()

                )







                .exceptionHandling(exception -> exception



                        // No JWT / invalid JWT

                        .authenticationEntryPoint(
                                authenticationEntryPoint()
                        )



                        // JWT exists but role denied

                        .accessDeniedHandler(
                                accessDeniedHandler()
                        )

                )







                .addFilterBefore(

                        jwtAuthenticationFilter,

                        UsernamePasswordAuthenticationFilter.class

                );





        return http.build();

    }









    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){


        return (request,response,exception) -> {


            response.setStatus(401);


            response.setContentType(
                    "application/json"
            );



            response.getWriter().write(

                    """
                    {
                      "status":401,
                      "error":"Unauthorized",
                      "message":"JWT token missing or invalid"
                    }
                    """

            );


        };


    }









    @Bean
    public AccessDeniedHandler accessDeniedHandler(){


        return (request,response,exception) -> {


            response.setStatus(403);


            response.setContentType(
                    "application/json"
            );



            response.getWriter().write(

                    """
                    {
                      "status":403,
                      "error":"Forbidden",
                      "message":"You do not have permission to access this resource"
                    }
                    """

            );


        };


    }


}