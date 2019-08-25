package com.eseasky.core.framework.AuthService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.UserTools.UserToolsService;
import com.eseasky.core.framework.AuthService.UserTools.entity.ExecuteSQLEntity;
import com.eseasky.global.entity.ResultModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "用户工具", tags = "用户工具服务")
@RestController
@Log4j2
@RequestMapping("/UserTools")
public class UserToolsController {
    @Autowired
    private UserToolsService userToolsService;

    @ApiOperation(value = "执行sql", httpMethod = "POST")
    @PostMapping(value = "/executeSQL")
    public ResultModel<List<List<Object>>> executeSQL(@RequestBody ExecuteSQLEntity executeSQL) {
        ResultModel<List<List<Object>>> msgReturn = new ResultModel<>();
        List<List<Object>> resList = null;
		try {
			resList = userToolsService.executeSQL(executeSQL);
	        log.info(JSONObject.toJSONString(resList));
	        msgReturn.setData(resList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msgReturn.setSubCode(500);
			msgReturn.setMessage(e.getMessage());
		}
        return msgReturn;
    }
}
