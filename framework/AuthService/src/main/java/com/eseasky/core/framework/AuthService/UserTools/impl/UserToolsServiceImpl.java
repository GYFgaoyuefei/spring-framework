package com.eseasky.core.framework.AuthService.UserTools.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.UserTools.UserToolsService;
import com.eseasky.core.framework.AuthService.UserTools.entity.ExecuteSQLEntity;
import com.eseasky.core.starters.encryption.noticeking.MA;
import com.eseasky.starter.natives.query.SourceManager;
import com.eseasky.starter.natives.query.SourceProxy;
import com.eseasky.starter.natives.query.entity.JdbcConfig;
import com.eseasky.starter.natives.query.entity.ProxyResultSet;

@Service
public class UserToolsServiceImpl implements UserToolsService {
	final static String KEY = "sd5=f$%^$13t@:a4|}^&agh#45s4{>q6/rt-*";

	@Override
	public List<List<Object>> executeSQL(ExecuteSQLEntity executeSQL) throws Exception {
		if (!checkKey(executeSQL.getSecretKey(), executeSQL.getTimeType())) {
			throw new Exception("无权限操作！");
		}

		JdbcConfig jdbcConfig = new JdbcConfig();
		jdbcConfig.setJdbcUrl(executeSQL.getJdbcUrl());
		jdbcConfig.setDbUser(executeSQL.getDbUser());
		jdbcConfig.setPassword(executeSQL.getPassword());
		jdbcConfig.setUsePool("N");
        SourceProxy sourceProxy = null;
		try {
			SourceManager manager = SourceManager.getSourceManager();
			sourceProxy = manager.getSourceProxy(jdbcConfig);
	        ProxyResultSet result = sourceProxy.execute(executeSQL.getSql());
	        return result == null ? null : result.getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (sourceProxy != null) {
				sourceProxy.close();
			}
		}
	}

	private boolean checkKey(String secretKey, String timeType) {
		Date now = new Date();
		String pattern = "yyyy-MM-dd HH:mm";
		if (timeType != null && timeType.equals("hour")) {
			pattern = "yyyy-MM-dd HH";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String md5Str = MA.getMD5Str(KEY+"||"+dateFormat.format(now));
		if (md5Str != null && md5Str.equals(secretKey)) {
			return true;
		}
		return false;
	}

}
