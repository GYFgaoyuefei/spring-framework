package com.eseasky.core.framework.Logger.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eseasky.core.framework.Logger.core.LogSaveAction;
import com.eseasky.core.framework.Logger.drivers.mysql.model.LoggerModel;
import com.eseasky.core.framework.Logger.protocol.dto.LogDTO;
import com.eseasky.core.framework.Logger.protocol.vo.LogVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/logger")
@Api(value = "日志记录", tags = "日志记录")
public class LoggerController {
	
	@Autowired
	LogSaveAction logSaveAction;
	
    @ApiOperation(value = "分页查询菜单主页",httpMethod = "POST")
    @PostMapping(value = "/queryLogByPage", consumes="application/json")
    public ResultModel<List<LogVO>> queryServMenuByPage(@RequestBody LogDTO logDTO)  {
    	ResultModel<List<LogVO>> msgReturn = new ResultModel<>();
    	Page<LoggerModel> pageLoggerModel = logSaveAction.queryServMenuByPage(logDTO);
		List<LogVO> listLogVO = pageLoggerModel.stream().map(item->{
			LogVO logVO = new LogVO();
			BeanUtils.copyProperties(item, logVO);
			return logVO;
		}).collect(Collectors.toList());
		msgReturn.setData(listLogVO, MsgPageInfo.loadFromPageable(pageLoggerModel));
        return msgReturn;
    }
}
