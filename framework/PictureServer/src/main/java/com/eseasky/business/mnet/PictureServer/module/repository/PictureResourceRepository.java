package com.eseasky.business.mnet.PictureServer.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eseasky.business.mnet.PictureServer.module.model.FileResourceInfo;

@Repository
public interface PictureResourceRepository extends JpaRepository<FileResourceInfo, Long>,JpaSpecificationExecutor<FileResourceInfo> {
	FileResourceInfo findByFileMd5(String md5);
	FileResourceInfo findByFileMd5AndResourceType(String md5, String resourceType);
	FileResourceInfo  findByFileId(String fileId);


}
