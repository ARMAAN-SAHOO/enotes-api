package com.armaan.enotes.security.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.armaan.enotes.security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException ,IOException{

           String authHeader=request.getHeader("Authorization");

           if(authHeader!=null && authHeader.startsWith("Bearer ")){

            String token=authHeader.substring(7);
            String userName=jwtUtil.extractUsername(token);

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);

                if(jwtUtil.validateToken(token, userDetails)){

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                    new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
           }
           
        filterChain.doFilter(request, response);
    }

}
