package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgInsertInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrganizeQuery;
import com.eseasky.core.starters.organization.persistence.model.OrganizeDefined;

@Service
public class OrgServiceImpl implements OrgService {
	@Autowired
	private IOrganizeService iOrganizeService;

	@Override
	public Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		Page<OrgQueryVO> orgQueryVOs=null;
		if(orgQueryDTO!=null) {
			OrganizeQuery organizeQuery=new OrganizeQuery();
			BeanUtils.copyProperties(orgQueryDTO, organizeQuery);
			Page<OrganizeDefined> organizeDefineds=iOrganizeService.queryOrganize(organizeQuery);
			if(organizeDefineds!=null) {
				List<OrgQueryVO> orgQueryVOls=organizeDefineds.stream().map(item->{
					OrgQueryVO orgQueryVO=new OrgQueryVO();
					BeanUtils.copyProperties(item, orgQueryVO);
					return orgQueryVO;
				}).collect(Collectors.toList());
				orgQueryVOs=new PageImpl<OrgQueryVO>(orgQueryVOls,organizeDefineds.getPageable(),organizeDefineds.getTotalElements());
			}
		}
		return orgQueryVOs;
	}
	
	@Override
	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		OrgSaveVO orgSaveVO=null;
		if(orgSaveDTO!=null){
			OrgInsertInfo orgInsertInfo=new OrgInsertInfo();
			BeanUtils.copyProperties(orgSaveDTO, orgInsertInfo);
			OrganizeDefined organizeDefined=iOrganizeService.addOrganize(orgInsertInfo);
			if(organizeDefined!=null){
				orgSaveVO=new OrgSaveVO();
				BeanUtils.copyProperties(organizeDefined, orgSaveVO);
			}
		}
		return orgSaveVO;
	}
}
