package com.nikita.springbootpj.config.security;

import com.nikita.springbootpj.services.implementations.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserInfoService userDetailsService;
    private final JwtProvider jwtProvider;
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws SecurityException, IOException, ServletException {

        String requestHeader = request.getHeader("Authorization"); //my header param
        logger.info("Header: {}",requestHeader);

        String username = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer")){ //my prefix
            token = requestHeader.substring(7); //get token after the word bearer

            try{
                username = this.jwtProvider.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                logger.info("Illegal Argument");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                logger.info("Expired token");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                logger.info("Altered Token");
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            logger.info("Header Value not valid");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtProvider.validateToken(token, userDetails);
            if(validateToken){

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }else{
                logger.info("Failed to validate");
            }

        }
        filterChain.doFilter(request,response);
    }


}


