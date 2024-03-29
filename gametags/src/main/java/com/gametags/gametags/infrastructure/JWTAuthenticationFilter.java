package com.gametags.gametags.infrastructure;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTGenerator tokenGenerator;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getJWTFromRequest(request);
    HttpSession session = request.getSession(true);
    if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)){
      String username = tokenGenerator.getUsernameFromJWT(token);
      logger.info("USUARIO: " + username);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      logger.info("AUTORIDADES: " + userDetails.getAuthorities().toString());
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }else{
      logger.info("NO HAY TOKEN");
    }
    filterChain.doFilter(request,response);
  }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
      return bearerToken.substring(7);
    }
    return null;
  }
}
