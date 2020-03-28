package com.gazbert.restsample.api.security.authentication;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gazbert.restsample.api.security.jwt.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests the JWT Authentication Entry Point behaves as expected.
 *
 * @author gazbert
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJwtAuthenticationEntryPoint {

  @MockBean private HttpServletRequest request;
  @MockBean private HttpServletResponse response;
  @MockBean private AuthenticationException authException;

  // Need these even though not used in the test directly because Spring loads them on startup...
  @MockBean private JwtUtils jwtUtils;
  @MockBean private AuthenticationManager authenticationManager;

  @Test
  public void whenCommenceCalledThenExpectUnauthorizedResponse() throws Exception {
    final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint =
        new JwtAuthenticationEntryPoint();
    jwtAuthenticationEntryPoint.commence(request, response, authException);
    verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
