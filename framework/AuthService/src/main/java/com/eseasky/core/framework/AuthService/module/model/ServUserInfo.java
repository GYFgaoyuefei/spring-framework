package com.eseasky.core.framework.AuthService.module.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.eseasky.core.starters.auth.organize.OrganizeEntity;
import com.eseasky.core.starters.auth.organize.OrganizeEntityListener;

import lombok.Data;

@Entity
@Data
@Table(name = "serv_user_info", indexes = {
		@Index(name="serv_user_info_index_userName", columnList = "userName", unique = true),
		@Index(name="serv_user_info_index_mobile", columnList = "mobile", unique = true),
		@Index(name="serv_user_info_index_orgCode", columnList = "orgCode"),
		@Index(name="serv_user_info_index_nickName", columnList = "nickName"),
})
@OrganizeEntity(fieldName = "org_code", name="orgCode", bootSkip = true)
@EntityListeners(value = { OrganizeEntityListener.class })
public class ServUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String passWord;
    private String mobile;
    private String state;
    private String orgCode;
//    @Column(columnDefinition="varchar(128) default '000'")
//    private String organCode="000";
//    private String orgName;
    private String nickName;
    private String loginAvailable;
}
