package com.gazbert.restsample.api.security.model;

/**
 * These Role names map to the Spring Security roles.
 *
 * <p>They are assigned to users in the resources/import.sql script when the app bootstraps.
 *
 * <p>See:
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#appendix-faq-role-prefix
 *
 * @author gazbert
 */
public enum RoleName {
  ROLE_USER,
  ROLE_ADMIN
}
