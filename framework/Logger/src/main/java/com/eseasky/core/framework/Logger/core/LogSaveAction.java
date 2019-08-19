package com.eseasky.core.framework.Logger.core;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.Logger.drivers.mysql.model.LoggerModel;
import com.eseasky.core.framework.Logger.exception.LogSaveFailedException;
import com.eseasky.core.framework.Logger.protocol.dto.LogDTO;
import com.eseasky.global.entity.HttpMessage;

public interface LogSaveAction {
	void saveLog(HttpMessage message) throws LogSaveFailedException;
	
	Page<LoggerModel> queryServMenuByPage(LogDTO logDTO);
	
	void delLogTask();
}
