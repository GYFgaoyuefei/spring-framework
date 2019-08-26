package com.eseasky.core.framework.AuthService.module.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "oauth_access_token", indexes= {
		@Index(name="token_id_index", columnList="tokenId"),
		@Index(name="authentication_id_index", columnList="authenticationId"),
		@Index(name="user_name_index", columnList="userName"),
		@Index(name="client_id_index", columnList="clientId"),
		@Index(name="refresh_token_index", columnList="refreshToken")
})
public class AuthAccessToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String tokenId;
	
	private Timestamp createTime;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private byte[] token;
	
	private String authenticationId;
	
	private String userName;
	
	private String clientId;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private byte[] authentication;
	
	private String refreshToken;
}
