package com.universe.usersSecurity.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.universe.usersSecurity.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

// cada vez que se haga una peti pasa por este filtro, para pedir el token
public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    // metodo que nos trae el extend OncePerRequestFilter
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // obtenemos el header, para conseguir la AUTHORIZATION
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (jwtToken != null) {

            // quitamos la palabra "bearer" del token
            jwtToken = jwtToken.substring(7);

            // usamos validateToken para recibir el token descodificado
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            // extraemos el usuario y las autorizaciones del token
            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            // spring security gestiona los permisos como GrantedAuthority, y usamos AuthorityUtils para hacer la lista de los permisos
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            // creamos un contexto para insertarlo en el security context holder, de spring security
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }

        // sino viene el token rechazamos la peticion, y pasa para el sig filtro
        filterChain.doFilter(request, response);
    }
}
