package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.service.RoleService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantedItemVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgUserGrantedVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedItem;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedQuery;
import com.eseasky.core.starters.organization.persistence.entity.OrgInsertInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgUpdateInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.ResourceQuery;
import com.eseasky.core.starters.organization.persistence.model.OrganizeDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeResourceDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeUserGranted;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private IOrganizeService iOrganizeService;
	
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

	@Override
	public Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> resoureQueryVOs=null;
		if(resoureQueryDTO!=null) {
			ResourceQuery resourceQuery=new ResourceQuery();
			BeanUtils.copyProperties(resoureQueryDTO, resourceQuery);
			Page<OrganizeResourceDefined> organizeDefineds=iOrganizeService.getResourceItems(resourceQuery);
			if(organizeDefineds!=null) {
				List<ResoureQueryVO> resoureQueryVOls=organizeDefineds.stream().map(item->{
					ResoureQueryVO resoureQueryVO=new ResoureQueryVO();
					BeanUtils.copyProperties(item, resoureQueryVO);
					return resoureQueryVO;
				}).collect(Collectors.toList());
				resoureQueryVOs=new PageImpl<ResoureQueryVO>(resoureQueryVOls,organizeDefineds.getPageable(),organizeDefineds.getTotalElements());
			}
		}
		return resoureQueryVOs;
	}


	@Override
	public OrgGrantInfoVO grant(OrgGrantInfoDTO orgGrantInfoDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO=null;
		if(orgGrantInfoDTO!=null) {
			OrgGrantInfo orgGrantInfo=new OrgGrantInfo();
			BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfo);
			OrgUserGranted orgUserGranted=iOrganizeService.grant(orgGrantInfo);
			if(orgUserGranted!=null) {
				orgGrantInfoVO=new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}

	@Override
	public OrgGrantInfoVO updateGrant(OrgUpdateGrantDTO orgUpdateGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO=null;
		if(orgUpdateGrantDTO!=null) {
			OrgUpdateInfo orgUpdateInfo=new OrgUpdateInfo();
			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
			OrgUserGranted orgUserGranted=iOrganizeService.updateGranted(orgUpdateInfo);
			if(orgUserGranted!=null) {
				orgGrantInfoVO=new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}


	@Override
	public Page<OrgGrantedItemVO> queryGranted(OrgQueryGrantDTO orgGrantedItemDTO) {
		// TODO Auto-generated method stub
		Page<OrgGrantedItemVO> orgGrantedItemVOs=null;
		if(orgGrantedItemDTO!=null) {
			OrgGrantedQuery orgGrantedQuery=new OrgGrantedQuery();
			BeanUtils.copyProperties(orgGrantedItemDTO, orgGrantedQuery);
			Page<OrgGrantedItem> orgGrantedItems=iOrganizeService.queryGranted(orgGrantedQuery);
			if(orgGrantedItems!=null) {
				List<OrgGrantedItemVO> orgGrantedItemVOls=orgGrantedItems.stream().map(item->{
					OrgGrantedItemVO orgGrantedItemVO=new OrgGrantedItemVO();
					BeanUtils.copyProperties(item, orgGrantedItemVO);
					return orgGrantedItemVO;
				}).collect(Collectors.toList());
				orgGrantedItemVOs=new PageImpl<OrgGrantedItemVO>(orgGrantedItemVOls,orgGrantedItems.getPageable(),orgGrantedItems.getTotalElements());
			}
		}
		return orgGrantedItemVOs;
	}


	@Override
	public Page<OrgUserGrantedVO> queryOrgUserGranted(OrgQueryGrantDTO orgQueryGrantDTO) {
		// TODO Auto-generated method stub
		Page<OrgUserGrantedVO> orgUserGrantedVOs=null;
		if(orgQueryGrantDTO!=null) {
			OrgGrantedQuery orgGrantedQuery=new OrgGrantedQuery();
			BeanUtils.copyProperties(orgQueryGrantDTO, orgGrantedQuery);
			Page<OrganizeUserGranted> orgGrantedItems=iOrganizeService.queryOrgUserGranted(orgGrantedQuery);
			if(orgGrantedItems!=null) {
				List<OrgUserGrantedVO> orgGrantedItemVOls=orgGrantedItems.stream().map(item->{
					OrgUserGrantedVO orgGrantedItemVO=new OrgUserGrantedVO();
					BeanUtils.copyProperties(item, orgGrantedItemVO);
					if(item.getResource()!=null) {
						ResoureQueryVO resource=new ResoureQueryVO();
						BeanUtils.copyProperties(item.getResource(), resource);
						orgGrantedItemVO.setResource(resource);
					}
					return orgGrantedItemVO;
				}).collect(Collectors.toList());
				orgUserGrantedVOs=new PageImpl<OrgUserGrantedVO>(orgGrantedItemVOls,orgGrantedItems.getPageable(),orgGrantedItems.getTotalElements());
			}
		}
		return orgUserGrantedVOs;
	}

	@Override
	public OrgGrantInfoVO deleteGrant(OrgUpdateGrantDTO orgUpdateGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO=null;
		if(orgUpdateGrantDTO!=null) {
			OrgUpdateInfo orgUpdateInfo=new OrgUpdateInfo();
			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
			OrgUserGranted orgUserGranted=iOrganizeService.deleteGranted(orgUpdateInfo);
			if(orgUserGranted!=null) {
				orgGrantInfoVO=new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}
}
