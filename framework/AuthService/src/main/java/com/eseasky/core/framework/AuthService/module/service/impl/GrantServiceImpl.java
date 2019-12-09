package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eseasky.core.framework.AuthService.module.service.GrantService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfosDTO;
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
public class GrantServiceImpl implements GrantService {
	@Autowired
	private IOrganizeService iOrganizeService;

	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		OrgSaveVO orgSaveVO = null;
		if (orgSaveDTO != null) {
			OrgInsertInfo orgInsertInfo = new OrgInsertInfo();
			BeanUtils.copyProperties(orgSaveDTO, orgInsertInfo);
			OrganizeDefined organizeDefined = iOrganizeService.addOrganize(orgInsertInfo);
			if (organizeDefined != null) {
				orgSaveVO = new OrgSaveVO();
				BeanUtils.copyProperties(organizeDefined, orgSaveVO);
			}
		}
		return orgSaveVO;
	}

	@Override
	public Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> resoureQueryVOs = null;
		if (resoureQueryDTO != null) {
			ResourceQuery resourceQuery = new ResourceQuery();
			BeanUtils.copyProperties(resoureQueryDTO, resourceQuery);
			if (resoureQueryDTO.getSize() != 0)
				resourceQuery.setPageSize(resoureQueryDTO.getSize());
			List<OrganizeUserGranted> organizeUserGranteds = null;
			Page<OrganizeResourceDefined> organizeDefineds = iOrganizeService.getResourceItems(resourceQuery);
			if (resoureQueryDTO.getUser() != null && resoureQueryDTO.getOrgCode() != null) {
				OrgGrantedQuery orgGrantedQuery = new OrgGrantedQuery();
				orgGrantedQuery.setUser(resoureQueryDTO.getUser());
//				orgGrantedQuery.setOrgCode(resoureQueryDTO.getOrgCode());
				// 目前只有分页接口，所有默认某组织用户权限少于100000，后期有接口再改
				orgGrantedQuery.setPageSize(100000);
				Page<OrganizeUserGranted> orgGrantedItems = iOrganizeService.queryOrgUserGranted(orgGrantedQuery);
				organizeUserGranteds = orgGrantedItems == null ? null : orgGrantedItems.getContent();
			}
			if (organizeDefineds != null) {
				List<ResoureQueryVO> resoureQueryVOls = new ArrayList<ResoureQueryVO>();
				for (OrganizeResourceDefined organizeResourceDefineds : organizeDefineds.getContent()) {
					ResoureQueryVO resoureQueryVO = new ResoureQueryVO();
					BeanUtils.copyProperties(organizeResourceDefineds, resoureQueryVO);
					if (organizeUserGranteds != null) {
						for (OrganizeUserGranted organizeUserGranted : organizeUserGranteds) {
							if (resoureQueryVO.getId() == organizeUserGranted.getResource().getId() && resoureQueryDTO.getOrgCode().startsWith(organizeUserGranted.getOrgCode())) {
								int action=organizeUserGranted.getAction()|resoureQueryVO.getAction();
								resoureQueryVO.setActionArr(transToBin(action));
							}
						}
					}
					resoureQueryVOls.add(resoureQueryVO);
				}
				resoureQueryVOs = new PageImpl<ResoureQueryVO>(resoureQueryVOls, organizeDefineds.getPageable(),
						organizeDefineds.getTotalElements());
			}
		}
		return resoureQueryVOs;
	}

	@Override
	public OrgGrantInfoVO grant(OrgGrantInfoDTO orgGrantInfoDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO = null;
		if (orgGrantInfoDTO != null) {
			OrgGrantInfo orgGrantInfo = new OrgGrantInfo();
			BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfo);
			orgGrantInfo.setAction(transToInt(orgGrantInfoDTO.getAction()));
			OrgUserGranted orgUserGranted = iOrganizeService.grant(orgGrantInfo);
			if (orgUserGranted != null) {
				orgGrantInfoVO = new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}

	@Override
	public OrgGrantInfoVO updateGrant(OrgUpdateGrantDTO orgUpdateGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO = null;
		if (orgUpdateGrantDTO != null) {
			OrgUpdateInfo orgUpdateInfo = new OrgUpdateInfo();
			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
			OrgUserGranted orgUserGranted = iOrganizeService.updateGranted(orgUpdateInfo);
			if (orgUserGranted != null) {
				orgGrantInfoVO = new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}

	@Override
	public Page<OrgGrantedItemVO> queryGranted(OrgQueryGrantDTO orgGrantedItemDTO) {
		// TODO Auto-generated method stub
		Page<OrgGrantedItemVO> orgGrantedItemVOs = null;
		if (orgGrantedItemDTO != null) {
			OrgGrantedQuery orgGrantedQuery = new OrgGrantedQuery();
			BeanUtils.copyProperties(orgGrantedItemDTO, orgGrantedQuery);
			if (orgGrantedItemDTO.getSize() != 0)
				orgGrantedQuery.setPageSize(orgGrantedItemDTO.getSize());
			Page<OrgGrantedItem> orgGrantedItems = iOrganizeService.queryGranted(orgGrantedQuery);
			if (orgGrantedItems != null) {
				List<OrgGrantedItemVO> orgGrantedItemVOls = orgGrantedItems.stream().map(item -> {
					OrgGrantedItemVO orgGrantedItemVO = new OrgGrantedItemVO();
					BeanUtils.copyProperties(item, orgGrantedItemVO);
					return orgGrantedItemVO;
				}).collect(Collectors.toList());
				orgGrantedItemVOs = new PageImpl<OrgGrantedItemVO>(orgGrantedItemVOls, orgGrantedItems.getPageable(),
						orgGrantedItems.getTotalElements());
			}
		}
		return orgGrantedItemVOs;
	}

	@Override
	public Page<OrgUserGrantedVO> queryOrgUserGranted(OrgQueryGrantDTO orgQueryGrantDTO) {
		// TODO Auto-generated method stub
		Page<OrgUserGrantedVO> orgUserGrantedVOs = null;
		if (orgQueryGrantDTO != null) {
			OrgGrantedQuery orgGrantedQuery = new OrgGrantedQuery();
			BeanUtils.copyProperties(orgQueryGrantDTO, orgGrantedQuery);
			if (orgQueryGrantDTO.getSize() != 0)
				orgGrantedQuery.setPageSize(orgQueryGrantDTO.getSize());
			Page<OrganizeUserGranted> orgGrantedItems = iOrganizeService.queryOrgUserGranted(orgGrantedQuery);
			if (orgGrantedItems != null) {
				List<OrgUserGrantedVO> orgGrantedItemVOls = orgGrantedItems.stream().map(item -> {
					OrgUserGrantedVO orgGrantedItemVO = new OrgUserGrantedVO();
					BeanUtils.copyProperties(item, orgGrantedItemVO);
					if (item.getResource() != null) {
						ResoureQueryVO resource = new ResoureQueryVO();
						BeanUtils.copyProperties(item.getResource(), resource);
						orgGrantedItemVO.setResource(resource);
					}
					return orgGrantedItemVO;
				}).collect(Collectors.toList());
				orgUserGrantedVOs = new PageImpl<OrgUserGrantedVO>(orgGrantedItemVOls, orgGrantedItems.getPageable(),
						orgGrantedItems.getTotalElements());
			}
		}
		return orgUserGrantedVOs;
	}

	@Override
	public OrgGrantInfoVO deleteGrant(OrgUpdateGrantDTO orgUpdateGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO = null;
		if (orgUpdateGrantDTO != null) {
			OrgUpdateInfo orgUpdateInfo = new OrgUpdateInfo();
			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
			OrgUserGranted orgUserGranted = iOrganizeService.deleteGranted(orgUpdateInfo);
			if (orgUserGranted != null) {
				orgGrantInfoVO = new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		return orgGrantInfoVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<OrgGrantInfoVO> grant(OrgGrantInfosDTO orgGrantInfoDTOs) {
		// TODO Auto-generated method stub
		List<OrgGrantInfoVO> orgGrantInfoVOs=null;
		if(orgGrantInfoDTOs!=null && orgGrantInfoDTOs.getOrgGrantInfoDTOs()!=null) {
			orgGrantInfoVOs=new ArrayList<OrgGrantInfoVO>();
			for(OrgGrantInfoDTO orgGrantInfoDTO:orgGrantInfoDTOs.getOrgGrantInfoDTOs()) {
				OrgGrantInfoVO orgGrantInfoVO=grant(orgGrantInfoDTO);
				if(orgGrantInfoVO!=null)
					orgGrantInfoVOs.add(orgGrantInfoVO);
			}
		}
		return orgGrantInfoVOs;
	}
	
	private List<String> transToBin(int action) {
		List<String> actions=new ArrayList<String>();;
		actions.add(action/8==0?null:"1000");
		actions.add((action%8)/4==0?null:"0100");
		actions.add((action%8%4)/2==0?null:"0010");
		actions.add((action%8%4%2)/1==0?null:"0001");
		return actions;
	}
	
	private int transToInt(Set<String> actions) {
		int action=0;
		if(actions!=null) {
			for(String temAction:actions) {
				if(temAction!=null && !temAction.equals(""))
				action=Integer.parseInt(temAction,2);
			}
		}
		return action;
	}
}
