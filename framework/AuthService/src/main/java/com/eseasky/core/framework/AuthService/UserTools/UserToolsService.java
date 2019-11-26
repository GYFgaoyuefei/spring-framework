package com.eseasky.core.framework.AuthService.UserTools;

import java.util.List;

import com.eseasky.core.framework.AuthService.UserTools.entity.DatabaseEntity;
import com.eseasky.core.framework.AuthService.UserTools.entity.ExecuteSQLEntity;

import java.util.List;

public interface UserToolsService {

	List<List<Object>> executeSQL(ExecuteSQLEntity executeSQL) throws Exception ;

	String backupDatabase(DatabaseEntity databaseEntity) throws Exception;

	String restoreDatabase(DatabaseEntity databaseEntity) throws Exception;
	 void mytest();

	String deleteBackups(DatabaseEntity databaseEntity) throws Exception;

}
