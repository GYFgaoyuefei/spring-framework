package com.eseasky.core.framework.AuthService.UserTools.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DatabaseEntity implements Serializable {

    private static final long serialVersionUID = -5169309547780292869L;
    private String secretKey;

    private String timeType;

    @NotBlank(message = "command不可为null或者空!")
    private String command;

    @NotBlank(message = "ip不可为null或者空!")
    private String ip;

    @NotBlank(message = "user不可为null或者空!")
    private String user;

    @NotBlank(message = "password不可为null或者空!")
    private String password;

    @NotBlank(message = "databaseName不可为null或者空!")
    private String databaseName;

    @NotBlank(message = "savePath不可为null或者空!")
    private String savePath;

    @NotBlank(message = "fileName不可为null或者空!")
    private String fileName;
}
