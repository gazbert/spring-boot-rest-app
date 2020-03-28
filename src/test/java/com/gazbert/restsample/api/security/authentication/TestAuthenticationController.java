package com.gazbert.restsample.api.security.authentication;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazbert.restsample.api.security.jwt.JwtUser;
import com.gazbert.restsample.api.security.jwt.JwtUserFactory;
import com.gazbert.restsample.api.security.jwt.JwtUtils;
import com.gazbert.restsample.api.security.model.Role;
import com.gazbert.restsample.api.security.model.RoleName;
import com.gazbert.restsample.api.security.model.User;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Tests the Authentication Controller behaves as expected.
 *
 * <p>Code originated from the excellent JWT and Spring Boot example by Stephan Zerhusen:
 * https://github.com/szerhusenBC/jwt-spring-security-demo
 *
 * @author gazbert
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuthenticationController {

  @Autowired private WebApplicationContext context;
  @MockBean private AuthenticationManager authenticationManager;
  @MockBean private JwtUtils jwtUtils;
  @MockBean private UserDetailsService userDetailsService;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  @WithAnonymousUser
  public void expectAnonymousUsersToBeAbleToCallGetToken() throws Exception {
    final JwtAuthenticationRequest jwtAuthenticationRequest =
        new JwtAuthenticationRequest("not-being-tested-here", "not-being-tested-here");

    mockMvc
        .perform(
            post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  @WithAnonymousUser
  public void whenGetTokenCalledWithBadCredentialsThenExpectUnauthorizedResponse()
      throws Exception {
    final JwtAuthenticationRequest jwtAuthenticationRequest =
        new JwtAuthenticationRequest("user", "bad-password");

    when(authenticationManager.authenticate(ArgumentMatchers.any()))
        .thenThrow(new BadCredentialsException("invalid password!"));

    mockMvc
        .perform(
            post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenRefreshTokenCalledWithUserRoleThenExpectSuccessResponse() throws Exception {
    final Role role = new Role();
    role.setId(0L);
    role.setName(RoleName.ROLE_USER);
    final List<Role> roles = Collections.singletonList(role);

    final User user = new User();
    user.setUsername("username");
    user.setRoles(roles);
    user.setEnabled(true);
    user.setLastPasswordResetDate(new Date(System.currentTimeMillis() + 1000 * 1000));

    final JwtUser jwtUser = JwtUserFactory.create(user);

    when(jwtUtils.getUsernameFromTokenClaims(ArgumentMatchers.any()))
        .thenReturn(user.getUsername());
    when(userDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(jwtUser);
    when(jwtUtils.canTokenBeRefreshed(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(true);

    mockMvc.perform(get("/api/token/refresh")).andExpect(status().is2xxSuccessful());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void whenRefreshTokenCalledWithAdminRoleThenExpectSuccessResponse() throws Exception {
    final Role authority = new Role();
    authority.setId(1L);
    authority.setName(RoleName.ROLE_ADMIN);
    final List<Role> authorities = Collections.singletonList(authority);

    final User user = new User();
    user.setUsername("admin");
    user.setRoles(authorities);
    user.setEnabled(true);
    user.setLastPasswordResetDate(new Date(System.currentTimeMillis() + 1000 * 1000));

    final JwtUser jwtUser = JwtUserFactory.create(user);

    when(jwtUtils.getUsernameFromTokenClaims(ArgumentMatchers.any()))
        .thenReturn(user.getUsername());
    when(userDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(jwtUser);
    when(jwtUtils.canTokenBeRefreshed(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(true);

    mockMvc.perform(get("/api/token/refresh")).andExpect(status().is2xxSuccessful());
  }

  @Test
  @WithAnonymousUser
  public void whenRefreshTokenCalledBuyAnonymousUserThenExpectUnauthorizedResponse()
      throws Exception {
    mockMvc.perform(get("/api/token/refresh")).andExpect(status().isUnauthorized());
  }
}
