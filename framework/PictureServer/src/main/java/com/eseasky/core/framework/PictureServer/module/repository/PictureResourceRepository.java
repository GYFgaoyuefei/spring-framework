package com.eseasky.core.framework.PictureServer.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eseasky.core.framework.PictureServer.module.model.PictureResource;

@Repository
public interface PictureResourceRepository extends JpaRepository<PictureResource, Long>,JpaSpecificationExecutor<PictureResource> {
	PictureResource findByFileMd5(String md5);

}
