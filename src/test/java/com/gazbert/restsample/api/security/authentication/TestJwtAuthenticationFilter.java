package com.gazbert.restsample.api.security.authentication;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gazbert.restsample.api.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests the JWT Authentication Filter behaves as expected.
 *
 * @author gazbert
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJwtAuthenticationFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final String USERNAME = "bobafett";

  @MockBean private HttpServletRequest request;
  @MockBean private HttpServletResponse response;
  @MockBean private FilterChain filterChain;
  @MockBean private JwtUtils jwtUtils;
  @MockBean private Claims claims;

  // Need these even though not used in the test directly because Spring loads them on startup...
  @MockBean private AuthenticationManager authenticationManager;

  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Before
  public void setup() {
    jwtAuthenticationFilter = new JwtAuthenticationFilter();
    jwtAuthenticationFilter.setJwtUtils(jwtUtils);
  }

  @Test
  public void whenFilterCalledWithValidTokenThenExpectSuccessfulAuthenticationAndCallNextFilter()
      throws Exception {

    // Need to reset this in case previous test sets it
    SecurityContextHolder.getContext().setAuthentication(null);

    when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn("dummy-token");
    when(jwtUtils.validateTokenAndGetClaims((ArgumentMatchers.any()))).thenReturn(claims);
    when(jwtUtils.getUsernameFromTokenClaims((ArgumentMatchers.any()))).thenReturn(USERNAME);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    verify(jwtUtils, times(1)).validateTokenAndGetClaims(ArgumentMatchers.any());
    verify(jwtUtils, times(1)).getUsernameFromTokenClaims(ArgumentMatchers.any());
    verify(jwtUtils, times(1)).getRolesFromTokenClaims(ArgumentMatchers.any());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void whenFilterCalledWithoutAuthorizationHeaderThenCallNextFilter() throws Exception {
    when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void whenFilterCalledWithBearerTokenWithMissingUsernameThenCallNextFilter()
      throws Exception {
    when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(BEARER_PREFIX + "dummy-token");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    verify(jwtUtils, times(1)).getUsernameFromTokenClaims(ArgumentMatchers.any());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void whenFilterCalledWithTokenWithMissingUsernameThenCallNextFilter() throws Exception {
    when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn("dummy-token");
    when(jwtUtils.getUsernameFromTokenClaims((ArgumentMatchers.any()))).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    verify(jwtUtils, times(1)).getUsernameFromTokenClaims(ArgumentMatchers.any());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void whenFilterCalledWithInvalidTokenThenCallNextFilter() throws Exception {
    when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn("dummy-token");
    when(jwtUtils.getUsernameFromTokenClaims((ArgumentMatchers.any()))).thenReturn(USERNAME);
    when(jwtUtils.validateTokenAndGetClaims((ArgumentMatchers.any()))).thenReturn(claims);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    verify(jwtUtils, times(1)).getUsernameFromTokenClaims(ArgumentMatchers.any());
    verify(jwtUtils, times(1)).validateTokenAndGetClaims(ArgumentMatchers.any());
    verify(filterChain, times(1)).doFilter(request, response);
  }
}
