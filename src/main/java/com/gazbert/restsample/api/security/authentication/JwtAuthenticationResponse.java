package com.gazbert.restsample.api.security.authentication;

import javax.validation.constraints.NotNull;

/**
 * Encapsulates a JWT Authentication response. It wraps the JWT (Base64 encoded String).
 *
 * @author gazbert
 */
public class JwtAuthenticationResponse {

  @NotNull(message = "Token cannot be null")
  private String token;

  // For Jackson
  public JwtAuthenticationResponse() {
    token = "";
  }

  public JwtAuthenticationResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
