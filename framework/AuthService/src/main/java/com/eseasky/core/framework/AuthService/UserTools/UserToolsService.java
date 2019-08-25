package com.eseasky.core.framework.AuthService.UserTools;

import java.util.List;

import com.eseasky.core.framework.AuthService.UserTools.entity.ExecuteSQLEntity;

public interface UserToolsService {

	List<List<Object>> executeSQL(ExecuteSQLEntity executeSQL) throws Exception ;

}
