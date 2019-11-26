package com.eseasky.core.framework.AuthService.UserTools.impl;

import com.eseasky.core.framework.AuthService.UserTools.UserToolsService;
import com.eseasky.core.framework.AuthService.UserTools.entity.DatabaseEntity;
import com.eseasky.core.framework.AuthService.UserTools.entity.DatabaseProperties;
import com.eseasky.core.framework.AuthService.UserTools.entity.ExecuteSQLEntity;
import com.eseasky.core.starters.encryption.noticeking.MA;
import com.eseasky.starter.core.channel.SessionFactory;
import com.eseasky.starter.core.channel.SessionProxy;
import com.eseasky.starter.core.channel.ShellExecReturn;
import com.eseasky.starter.core.channel.UserInfo;
import com.eseasky.starter.natives.query.SourceManager;
import com.eseasky.starter.natives.query.SourceProxy;
import com.eseasky.starter.natives.query.entity.JdbcConfig;
import com.eseasky.starter.natives.query.entity.ProxyResultSet;
import lombok.extern.log4j.Log4j2;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class UserToolsServiceImpl implements UserToolsService {
    final static String KEY = "sd5=f$%^$13t@:a4|}^&agh#45s4{>q6/rt-*";

    @Autowired
    DatabaseProperties databaseProperties;

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

    @Override
    public String backupDatabase(DatabaseEntity databaseEntity) throws Exception {
        if (!checkKey(databaseEntity.getSecretKey(), databaseEntity.getTimeType())) {
            throw new Exception("无权限操作！");
        }
        backupscheduledtask();
        return "备份成功";
    }

    private boolean checkKey(String secretKey, String timeType) {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm";
        if (timeType != null && timeType.equals("hour")) {
            pattern = "yyyy-MM-dd HH";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String md5Str = MA.getMD5Str(KEY + "||" + dateFormat.format(now));
        if (md5Str != null && md5Str.equals(secretKey)) {
            return true;
        }
        return false;
    }

    //    @Scheduled(cron = "0 0 2 * * ? ")
    public void backupscheduledtask() throws Exception {
        DatabaseEntity databaseEntity = buildDatabaseInfo();
        doBackup(databaseEntity);
    }

    private String doBackup(DatabaseEntity databaseEntity) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setIp(databaseEntity.getIp());
        userInfo.setUsername(databaseEntity.getUser());
        userInfo.setPassword(databaseEntity.getPassword());
        SessionProxy sessionProxy = SessionFactory.getInstance().getCommandOperation(userInfo).getSessionProxy();
        ShellExecReturn shellExecReturn = sessionProxy.execReturn("mkdir " + databaseEntity.getSavePath(), 20 * 1000L);
        StringBuilder sb = new StringBuilder();
        sb.append(databaseEntity.getCommand());
        sb.append(" --default-character-set=utf8 ");
        sb.append(databaseEntity.getDatabaseName());
        sb.append(" > ");
        sb.append(databaseEntity.getSavePath());
        sb.append(databaseEntity.getFileName());
        log.info("backupDatabase:[{}]", sb.toString());
        shellExecReturn = sessionProxy.execReturn(sb.toString(), 20 * 1000L);
        log.info("ShellExecReturn为[{}]", shellExecReturn);

        if (shellExecReturn.getStatus() == 0) {// 0 表示线程正常终止。
            //备份完成后 删除除今天外的所有备份数据
            log.info("删除多余备份文件");
//            sessionProxy.execReturn("cd "+databaseEntity.getSavePath(),20*1000L);
            sessionProxy.execReturn("cd " + databaseEntity.getSavePath() + "\n " + "ls |grep -v " + databaseEntity.getFileName() + " |xargs rm -f", 20 * 1000L);
            return "数据库备份成功";
        } else {
            return shellExecReturn.getError().toString();
        }
    }

    private DatabaseEntity buildDatabaseInfo() {
        DatabaseEntity databaseEntity = new DatabaseEntity();

        databaseEntity.setIp(databaseProperties.getIp());
        databaseEntity.setUser(databaseProperties.getUser());
        databaseEntity.setPassword(databaseProperties.getPassword());
        databaseEntity.setDatabaseName(databaseProperties.getDatabaseName());
        databaseEntity.setSavePath(databaseProperties.getSavePath());
        databaseEntity.setCommand(databaseProperties.getBackupCommand());
        databaseEntity.setFileName(getSqlFileName(databaseProperties.getDatabaseName()));

        if (!databaseEntity.getSavePath().endsWith(File.separator)) {
            databaseEntity.setSavePath(databaseEntity.getSavePath() + File.separator);
        }
        //后缀统一换成.sql结尾
        String fileName = databaseEntity.getFileName();
        int i = fileName.lastIndexOf('.');
        if (i != -1) {
            databaseEntity.setFileName(fileName.substring(0, i) + ".sql");
        } else {
            databaseEntity.setFileName(fileName + ".sql");
        }
        return databaseEntity;
    }

    /**
     * 获取库名加当前时间 形式的sql文件
     *
     * @param databaseName 库名
     * @return sql文件名
     */
    private String getSqlFileName(String databaseName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return databaseName + sdf.format(new Date()) + ".sql";
    }

    @Override
    public String restoreDatabase(DatabaseEntity databaseEntity) throws Exception {
        if (!checkKey(databaseEntity.getSecretKey(), databaseEntity.getTimeType())) {
            throw new Exception("无权限操作！");
        }
        databaseEntity = buildDatabaseInfo();
        StringBuilder sb = new StringBuilder();
        //"mysql -h 192.168.25.129 -u root -p123456 --default-character-set=utf8 qinmei"
        sb.append(databaseEntity.getCommand());
        sb.append(" --default-character-set=utf8 ");
        sb.append(databaseEntity.getDatabaseName());
        sb.append(" < ");
        sb.append(databaseEntity.getSavePath());
        sb.append(databaseEntity.getFileName());
        log.info("restoreDatabase:[{}]", sb.toString());
        UserInfo userInfo = new UserInfo();
        userInfo.setIp(databaseEntity.getIp());
        userInfo.setUsername(databaseEntity.getUser());
        userInfo.setPassword(databaseEntity.getPassword());
        SessionProxy sessionProxy = SessionFactory.getInstance().getCommandOperation(userInfo).getSessionProxy();
        ShellExecReturn shellExecReturn = sessionProxy.execReturn(sb.toString(), 20 * 1000L);
        log.info("ShellExecReturn为[{}]", shellExecReturn);
        if (shellExecReturn.getStatus() == 0) {// 0 表示线程正常终止。
            return "数据库还原成功";
        } else {
            return shellExecReturn.getError().toString();
        }
    }

    @Override
    public void mytest() {
        System.out.println(databaseProperties.getDatabaseName());
    }
//
//    public static void main(String[] args) {
//        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        //加密所需的salt(盐)
//        textEncryptor.setPassword("LEiSHEnK750");          //G0CvDz7oJn6
//        //要加密的数据（数据库的用户名或密码）
//        String username = textEncryptor.encrypt("mnet");
//        String password = textEncryptor.encrypt("mnet@123"); //(decrypt这个的解密方法)
//        System.out.println("加密username:" + username);
//        System.out.println("加密password:" + password);
//
//        System.out.println(textEncryptor.decrypt("DYFqq8GgjB5VsRr/ilOyogpgkIlQjwew"));
//    }
}
