package com.auditsphere.auditspherebackend.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;



@Component
public class JwtUtil {


    private final String SECRET =
            "auditsphereSecretKeyForJwtAuthentication123456789";



    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes()
            );





    // Generate JWT Token
    public String generateToken(
            String email,
            String role
    ) {

        return Jwts.builder()

                .subject(email)

                .claim(
                        "role",
                        role
                )

                .issuedAt(
                        new Date()
                )

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 10
                        )
                )

                .signWith(key)

                .compact();
    }

    // Extract email from JWT

    public String extractEmail(String token){


        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getSubject();

    }





    // Extract role from JWT

    public String extractRole(String token){


        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .get("role", String.class);

    }





    // Validate JWT

    public boolean validateToken(String token){

        try{


            Jwts.parser()

                    .verifyWith(key)

                    .build()

                    .parseSignedClaims(token);


            return true;


        }
        catch(Exception e){

            return false;

        }

    }

}