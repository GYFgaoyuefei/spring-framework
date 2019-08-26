package com.eseasky.core.framework.system.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eseasky.core.framework.system.models.entity.DictItem;




public interface DictItemRepository extends JpaRepository<DictItem, Long> {
	
}