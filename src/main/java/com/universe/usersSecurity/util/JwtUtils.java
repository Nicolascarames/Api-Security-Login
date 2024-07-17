package com.universe.usersSecurity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    // Recibimos en params este objeto para sacar el usuario y las autorizaciones
    public String createToken(Authentication authentication) {

        //creamos el algorithm
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        //obtenemos el usuario del parametro
        String username = authentication.getPrincipal().toString();

        //obtenemos las autorizaciones del parametro tambien
        //.getAuthorities(), esto nos devuelve una coleccion con las autorizaciones y lo transformamos para tener un string separado por comas
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //generamos el token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)//usuario que genera el token , esta en env
                .withSubject(username) // al usuario que se le genera el token, para meter en el payload
                .withClaim("authorities", authorities) // claim = payload
                .withIssuedAt(new Date()) // fecha en la que se genera el token
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // fecha que el token ya no es valido
                .withJWTId(UUID.randomUUID().toString()) // asignamos un id al token
                .withNotBefore(new Date(System.currentTimeMillis())) // a partir de cuando este token es valido
                .sign(algorithm); // firma del token, el algoritmo de arriba

        return jwtToken;
    }

    // validamos el token y nos devuelve el token descodificado
    public DecodedJWT validateToken(String token) {
        try {
            // creamos el algoritmo
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            // para verificar , necesitamos el algoritmo y el usuario que creo el token
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            // si el .verify sale bien retornara el token descodificado , sino lanza un error
            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;

        } catch (JWTVerificationException exception) {
            // si entramos aqui quiere decir que el token no es valido
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        // extraemos el usuario del payload, a partir del token descodificado
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        // para extraer un claim del token descodificado
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
