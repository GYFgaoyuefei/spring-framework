package com.eseasky.core.framework.AuthService.module.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "serv_login_code", indexes = {
		@Index(name="serv_login_code_index_phone", columnList = "phone", unique = true),
})
public class ServLoginCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Timestamp createTime;
    
    private String code;
    
    private String phone;
}
