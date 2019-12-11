package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eseasky.core.framework.AuthService.module.service.GrantService;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
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
import com.eseasky.core.framework.AuthService.utils.BinOrListUtil;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedItem;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedQuery;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedUpdateInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgInsertInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.ResourceQuery;
import com.eseasky.core.starters.organization.persistence.model.OrganizeDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeResourceDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeUserGranted;

@Service
public class GrantServiceImpl implements GrantService {
	@Autowired
	private IOrganizeService iOrganizeService;

	@Autowired
	private OrgService orgService;

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
				organizeUserGranteds=queryGrantByUser(resoureQueryDTO.getUser());
			}
			resoureQueryVOs=transToResVO(organizeDefineds, organizeUserGranteds, resoureQueryDTO.getOrgCode());
		}
		return resoureQueryVOs;
	}

	@Override
	public OrgGrantInfoVO grant(OrgGrantInfoDTO orgGrantInfoDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO = null;
		if (orgGrantInfoDTO != null) {
			OrgUserGranted orgUserGranted = null;
			if (orgGrantInfoDTO.getGrantId() != null) {
				OrgGrantedUpdateInfo orgGrantedUpdateInfo = new OrgGrantedUpdateInfo();
				orgGrantedUpdateInfo.setOrgCode(orgGrantInfoDTO.getOrgCode());
				orgGrantedUpdateInfo.setAction(BinOrListUtil.transToInt(orgGrantInfoDTO.getAction()));
				orgGrantedUpdateInfo.setId(orgGrantInfoDTO.getGrantId());
//				if(orgGrantedUpdateInfo.getAction()==null || orgGrantedUpdateInfo.getAction()==0 ||)
				orgUserGranted = iOrganizeService.deleteGranted(orgGrantedUpdateInfo);
			} 
				OrgGrantInfo orgGrantInfo = new OrgGrantInfo();
				BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfo);
				orgGrantInfo.setAction(BinOrListUtil.transToInt(orgGrantInfoDTO.getAction()));
				if(orgGrantInfo.getAction()!=null && orgGrantInfo.getAction()!=0)
				orgUserGranted = iOrganizeService.grant(orgGrantInfo);
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
//		if (orgUpdateGrantDTO != null) {
//			OrgUpdateInfo orgUpdateInfo = new OrgUpdateInfo();
//			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
//			OrgUserGranted orgUserGranted = iOrganizeService.updateGranted(orgUpdateInfo);
//			if (orgUserGranted != null) {
//				orgGrantInfoVO = new OrgGrantInfoVO();
//				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
//			}
//		}
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
//		if (orgUpdateGrantDTO != null) {
//			OrgUpdateInfo orgUpdateInfo = new OrgUpdateInfo();
//			BeanUtils.copyProperties(orgUpdateGrantDTO, orgUpdateInfo);
//			OrgUserGranted orgUserGranted = iOrganizeService.deleteGranted(orgUpdateInfo);
//			if (orgUserGranted != null) {
//				orgGrantInfoVO = new OrgGrantInfoVO();
//				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
//			}
//		}
		return orgGrantInfoVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<OrgGrantInfoVO> grant(OrgGrantInfosDTO orgGrantInfoDTOs) {
		// TODO Auto-generated method stub
		List<OrgGrantInfoVO> orgGrantInfoVOs = null;
		if (orgGrantInfoDTOs != null && orgGrantInfoDTOs.getOrgGrantInfoDTOs() != null) {
			orgGrantInfoVOs = new ArrayList<OrgGrantInfoVO>();
			for (OrgGrantInfoDTO orgGrantInfoDTO : orgGrantInfoDTOs.getOrgGrantInfoDTOs()) {
				OrgGrantInfoVO orgGrantInfoVO = grant(orgGrantInfoDTO);
				if (orgGrantInfoVO != null)
					orgGrantInfoVOs.add(orgGrantInfoVO);
			}
		}
		return orgGrantInfoVOs;
	}


	private List<OrganizeUserGranted> queryGrantByUser(String user) {
		List<OrganizeUserGranted> organizeUserGranteds = null;
		if (user != null) {
			OrgGrantedQuery orgGrantedQuery = new OrgGrantedQuery();
			orgGrantedQuery.setUser(user);
			int page = 0;
			while (true) {
				orgGrantedQuery.setPage(page);
				orgGrantedQuery.setPageSize(50);
				Page<OrganizeUserGranted> orgGrantedItems = iOrganizeService.queryOrgUserGranted(orgGrantedQuery);
				if (orgGrantedItems == null || orgGrantedItems.getContent() == null || orgGrantedItems.getContent().size()==0)
					break;
				if (organizeUserGranteds == null)
					organizeUserGranteds = orgGrantedItems.getContent();
				else
					organizeUserGranteds.addAll(orgGrantedItems.getContent());
				page++;
			}
		}
		return organizeUserGranteds;
	}
	
	private Page<ResoureQueryVO>  transToResVO(Page<OrganizeResourceDefined> organizeDefineds,List<OrganizeUserGranted> organizeUserGranteds,String orgCode) {
		Page<ResoureQueryVO> resoureQueryVOs=null;
		if (organizeDefineds != null&& orgCode!=null) {
			String temOrgCode=orgCode;
			List<ResoureQueryVO> resoureQueryVOls = new ArrayList<ResoureQueryVO>();
			for (OrganizeResourceDefined organizeResourceDefineds : organizeDefineds.getContent()) {
				ResoureQueryVO resoureQueryVO = new ResoureQueryVO();
				BeanUtils.copyProperties(organizeResourceDefineds, resoureQueryVO);
				if (organizeUserGranteds != null ) {
					for (OrganizeUserGranted organizeUserGranted : organizeUserGranteds) {
						if (resoureQueryVO.getId() == organizeUserGranted.getResource().getId()
								&& orgCode.startsWith(organizeUserGranted.getOrgCode())) {
							if (organizeUserGranted.getAction() > resoureQueryVO.getAction()) {
								int action = organizeUserGranted.getAction();
								resoureQueryVO.setActionArr(BinOrListUtil.transToBin(action));
								resoureQueryVO.setGrantId(organizeUserGranted.getId());
								temOrgCode=organizeUserGranted.getOrgCode();
//								resoureQueryVO.setOrgCode(organizeUserGranted.getOrgCode());
//								OrgSaveVO orgSaveVO = orgService
//										.getOrgNameByOrgCode(organizeUserGranted.getOrgCode());
//								if (orgSaveVO != null)
//									resoureQueryVO.setOrgName(orgSaveVO.getName());
							}
						}
					}
				}
				resoureQueryVO.setOrgCode(temOrgCode);
				OrgSaveVO orgSaveVO = orgService
						.getOrgNameByOrgCode(temOrgCode);
				if (orgSaveVO != null)
					resoureQueryVO.setOrgName(orgSaveVO.getName());
				resoureQueryVOls.add(resoureQueryVO);
			}
				resoureQueryVOs = new PageImpl<ResoureQueryVO>(resoureQueryVOls, organizeDefineds.getPageable(),
						organizeDefineds.getTotalElements());
			}
		return resoureQueryVOs;
	}
}
