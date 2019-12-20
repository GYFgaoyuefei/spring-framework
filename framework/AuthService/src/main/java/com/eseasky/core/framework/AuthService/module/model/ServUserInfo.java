package com.eseasky.core.framework.AuthService.module.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Data
@Table(name = "serv_user_info", indexes = {
		@Index(name="serv_user_info_index_userName", columnList = "userName", unique = true),
		@Index(name="serv_user_info_index_mobile", columnList = "mobile", unique = true),
		@Index(name="serv_user_info_index_orgCode", columnList = "orgCode"),
		@Index(name="serv_user_info_index_nikeName", columnList = "nikeName"),
})
//@OrganizeEntity(fieldName = "organ_code", name="organCode")
//@EntityListeners(value = { OrganizeEntityListener.class })
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
    private String nikeName;
}
