package com.gazbert.restsample.api.security.authentication;

import javax.validation.constraints.NotNull;

/**
 * Encapsulates a JWT Authentication Request containing username/password sent from the client.
 *
 * <p>When a client first connects, it will need to pass credentials in order to be authenticated
 * and issued a JWT for use in subsequent requests.
 *
 * @author gazbert
 */
public class JwtAuthenticationRequest {

  @NotNull(message = "Username cannot be null")
  private String username;

  @NotNull(message = "Password cannot be null")
  private String password;

  // For Jackson
  public JwtAuthenticationRequest() {
    username = "";
    password = "";
  }

  public JwtAuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
