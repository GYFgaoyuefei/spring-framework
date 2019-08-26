package com.eseasky.core.framework.system.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eseasky.core.framework.system.models.entity.Dictionary;



@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long>,JpaSpecificationExecutor<Dictionary> {
	List<Dictionary> findByType(String type);
	List<Dictionary> findByTypeAndStatus(String type, String status);
	Dictionary findByTypeAndGroup(String type, String group);
	List<Dictionary> findByTypeAndGroupInAndStatus(String type, List<String> groups, String status);
	Dictionary findByTypeAndGroupAndStatus(String type, String group, String status);
	List<Dictionary> findByStatus(String status);
	List<Dictionary> findByIdIn(List<Long> ids);
	
	@Query(value = "SELECT distinct(type) FROM sys_dictionary", nativeQuery = true)
	List<String> getDictTypes();

	@Query(value = "SELECT distinct(`group`) FROM sys_dictionary", nativeQuery = true)
	List<String> getDictGroups();
}
