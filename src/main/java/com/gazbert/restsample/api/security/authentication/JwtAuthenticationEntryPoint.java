package com.gazbert.restsample.api.security.authentication;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JWT 'login' entry point - Spring does not supply one for JWT Authentication.
 *
 * <p>We just send a 401 response, to which the client should call the /auth endpoint to fetch a JWT
 * for use in all subsequent requests.
 *
 * <p>Code originated from the excellent JWT and Spring Boot example by Stephan Zerhusen:
 * https://github.com/szerhusenBC/jwt-spring-security-demo
 *
 * @author gazbert
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * This is invoked when a user tries to access a secured REST resource without supplying any
   * credentials in the HTTP Authorization header.
   *
   * <p>We just send a 401 Unauthorized response because there is no 'login page' to redirect to.
   * The client should then post username/password to the /auth endpoint to obtain a JWT.
   *
   * @param request the incoming request.
   * @param response the outbound response.
   * @param authException the exception that got us here.
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
