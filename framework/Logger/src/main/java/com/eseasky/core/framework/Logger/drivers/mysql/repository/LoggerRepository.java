package com.eseasky.core.framework.Logger.drivers.mysql.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eseasky.core.framework.Logger.drivers.mysql.model.LoggerModel;

@Repository
public interface LoggerRepository extends CrudRepository<LoggerModel, Long>,JpaSpecificationExecutor<LoggerModel>{
	List<LoggerModel> findByResponseTimeBefore(Timestamp timestamp);
}
