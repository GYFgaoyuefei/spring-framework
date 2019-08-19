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
		@Index(name="token_id_index", columnList="token_id"),
		@Index(name="authentication_id_index", columnList="authentication_id"),
		@Index(name="user_name_index", columnList="user_name"),
		@Index(name="client_id_index", columnList="client_id"),
		@Index(name="refresh_token_index", columnList="refresh_token")
})
public class AuthAccessToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String token_id;
	
	private Timestamp create_time;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private byte[] token;
	
	private String authentication_id;
	
	private String user_name;
	
	private String client_id;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private byte[] authentication;
	
	private String refresh_token;
}
