package com.cloudnative.security.oauth.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysClient implements Serializable{
	/**
    *
    */
   private static final long serialVersionUID = -8185413579135897885L;
   private Long id;
   private String clientId;
   private String resourceIds = "";
   private String clientSecret;
   private String clientSecretStr;
   private String scope = "all";
   private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
   private String webServerRedirectUri;
   private String authorities = "";
   private Integer accessTokenValidity = 18000;
   private Integer refreshTokenValidity = 18000;
   private String additionalInformation = "{}";
   private String autoapprove = "true";
   private Boolean status ;
   
}
