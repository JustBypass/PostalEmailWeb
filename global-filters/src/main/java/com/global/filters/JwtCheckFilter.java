package com.global.filters;

import com.mail.global.clients.online.ClientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JwtCheckFilter extends OncePerRequestFilter {
  //  private final Logger log = LoggerFactory.getLogger(JwtCheckFilter.class);

    private final String AUTH_URL;

    public JwtCheckFilter(String authorizationUrl){
        this.AUTH_URL = authorizationUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  {
        //log.info("jwtFilter");
        try {
            var client = new RestTemplate().getForObject(
                    AUTH_URL,
                    ClientDTO.class,
                    Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+request.getHeader(HttpHeaders.AUTHORIZATION))
            );

            if(client!=null){
                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken(client,null,client.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(user);

                filterChain.doFilter(request,response);
            }
        }
        catch(Exception e)
        {
      //      log.error(e.getMessage(),"Something went wrong in jwt authorization");
        }
    }
}
