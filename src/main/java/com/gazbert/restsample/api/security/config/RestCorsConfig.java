package com.gazbert.restsample.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS config for the REST API.
 *
 * <p>Needed so that the browser will allow the REST API to be accessed on a different machine to
 * the one hosting a UI app.
 *
 * @author gazbert
 */
@Configuration
public class RestCorsConfig {

  /**
   * Creates the CORS filter.
   *
   * @return the CORS filter.
   */
  @Bean
  public CorsFilter corsFilter() {

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);

    // TODO: Lock down to specific host in Production
    config.addAllowedOrigin("*");
    // config.addAllowedOrigin("http://localhost:3000");

    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("DELETE");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
