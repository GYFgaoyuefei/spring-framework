package com.eseasky.core.framework.AuthService.UserTools.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DatabaseEntity implements Serializable {

    private static final long serialVersionUID = -5169309547780292869L;
    private String secretKey;

    private String timeType;


    private String command;


    private String ip;


    private String user;


    private String password;


    private String databaseName;

    private String savePath;

//    @NotBlank(message = "fileName不可为null或者空!")
    private String fileName;
}
