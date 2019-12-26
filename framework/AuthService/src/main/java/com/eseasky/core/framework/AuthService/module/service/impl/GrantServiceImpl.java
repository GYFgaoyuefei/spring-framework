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
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantedItemVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.framework.AuthService.utils.BinOrListUtil;
import com.eseasky.core.starters.organization.exception.OrgLogicException;
import com.eseasky.core.starters.organization.exception.OrgPersistenceException;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedItem;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedQuery;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedUpdateInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.ResourceQuery;
import com.eseasky.core.starters.organization.persistence.model.OrganizeResourceDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeUserGranted;

@Service
public class GrantServiceImpl implements GrantService {
	@Autowired
	private IOrganizeService iOrganizeService;

	@Autowired
	private OrgService orgService;

	@Override
	public Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> resoureQueryVOs = null;
		if (resoureQueryDTO != null) {
			ResourceQuery resourceQuery = new ResourceQuery();
			BeanUtils.copyProperties(resoureQueryDTO, resourceQuery);
			if (resoureQueryDTO.getSize() != 0)
				resourceQuery.setPageSize(resoureQueryDTO.getSize());
//			List<OrganizeUserGranted> organizeUserGranteds = null;
			Page<OrganizeResourceDefined> organizeDefineds = iOrganizeService.getResourceItems(resourceQuery);
			if (organizeDefineds != null) {
				List<ResoureQueryVO> temResoureQueryVOs = organizeDefineds.stream().map(item -> {
					ResoureQueryVO temResoureQueryVO = new ResoureQueryVO();
					BeanUtils.copyProperties(item, temResoureQueryVO, "orgCode");
					return temResoureQueryVO;
				}).collect(Collectors.toList());
				if (temResoureQueryVOs != null)
					resoureQueryVOs = new PageImpl<ResoureQueryVO>(temResoureQueryVOs, organizeDefineds.getPageable(),
							organizeDefineds.getTotalElements());
			}
//			if (resoureQueryDTO.getUser() != null && resoureQueryDTO.getOrgCode() != null) {
//				organizeUserGranteds=queryGrantByUser(resoureQueryDTO.getUser());
//			}
//			resoureQueryVOs=transToResVO(organizeDefineds, organizeUserGranteds, resoureQueryDTO.getOrgCode());
		}
		return resoureQueryVOs;
	}

	@Override
//	@Transactional(rollbackFor = Exception.class)
	public GrantInfoVO grant(OrgGrantInfoDTO orgGrantInfoDTO) {
		// TODO Auto-generated method stub
		GrantInfoVO orgGrantInfoVO = null;
		if (orgGrantInfoDTO != null) {
			OrgUserGranted orgUserGranted = null;
			try {
				if (orgGrantInfoDTO.getGrantId() != null) {
					OrgGrantedUpdateInfo orgGrantedUpdateInfo = new OrgGrantedUpdateInfo();
					orgGrantedUpdateInfo.setOrgCode(orgGrantInfoDTO.getOrgCode());
					orgGrantedUpdateInfo.setAction(BinOrListUtil.transToInt(orgGrantInfoDTO.getAction()));
					orgGrantedUpdateInfo.setId(orgGrantInfoDTO.getGrantId());
					orgGrantedUpdateInfo.setUpdateUser(orgGrantInfoDTO.getCreateUser());
					orgUserGranted = iOrganizeService.deleteGranted(orgGrantedUpdateInfo);

				}
				OrgGrantInfo orgGrantInfo = new OrgGrantInfo();
				BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfo);
				orgGrantInfo.setAction(BinOrListUtil.transToInt(orgGrantInfoDTO.getAction()));
				if (orgGrantInfo.getAction() != null && orgGrantInfo.getAction() != 0)
					orgUserGranted = iOrganizeService.grant(orgGrantInfo);
			} catch (OrgPersistenceException orgPersistenceException) {
				if (orgPersistenceException.getCode() == OrgLogicException.DUPLICATE_GRANTED) {
					orgGrantInfoVO = new GrantInfoVO();
					BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfoVO);
					orgGrantInfoVO.setMessage(orgPersistenceException.getMessage());
					return orgGrantInfoVO;
				} else
					throw orgPersistenceException;
			}
			if (orgUserGranted != null) {
				orgGrantInfoVO = new GrantInfoVO();
				BeanUtils.copyProperties(orgGrantInfoDTO, orgGrantInfoVO);
				orgGrantInfoVO.setMessage("授权成功");
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
	public Page<ResoureQueryVO> queryOrgUserGranted(OrgQueryGrantDTO orgQueryGrantDTO) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> orgUserGrantedVOs = null;
		if (orgQueryGrantDTO != null) {
			OrgGrantedQuery orgGrantedQuery = new OrgGrantedQuery();
			BeanUtils.copyProperties(orgQueryGrantDTO, orgGrantedQuery);
			if (orgQueryGrantDTO.getSize() != 0)
				orgGrantedQuery.setPageSize(orgQueryGrantDTO.getSize());
//			orgGrantedQuery.set("REJECT");
			Page<OrganizeUserGranted> orgGrantedItems = iOrganizeService.queryOrgUserGranted(orgGrantedQuery);
			if (orgGrantedItems != null) {
				List<ResoureQueryVO> orgGrantedItemVOls = orgGrantedItems.stream().filter(item->item.getGrantType().equals("REJECT")).map(item -> {
					ResoureQueryVO orgGrantedItemVO = new ResoureQueryVO();
					BeanUtils.copyProperties(item, orgGrantedItemVO, "id");
					orgGrantedItemVO.setGrantId(item.getId());
					orgGrantedItemVO.setActionArr(BinOrListUtil.transToBin(item.getAction()));
					orgGrantedItemVO.setOrgName(orgService.getOrgNameByOrgCode(item.getOrgCode()).getName());
					if (item.getResource() != null) {
						orgGrantedItemVO.setId(item.getResource().getId());
						orgGrantedItemVO.setResourceName(item.getResource().getResourceName());
						orgGrantedItemVO.setNote(item.getResource().getNote());
					}
					return orgGrantedItemVO;
				}).collect(Collectors.toList());
				orgUserGrantedVOs = new PageImpl<ResoureQueryVO>(orgGrantedItemVOls, orgGrantedItems.getPageable(),
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
	public List<GrantInfoVO> grant(OrgGrantInfosDTO orgGrantInfoDTOs) {
		// TODO Auto-generated method stub
		List<GrantInfoVO> orgGrantInfoVOs = null;
		if (orgGrantInfoDTOs != null && orgGrantInfoDTOs.getOrgGrantInfoDTOs() != null) {
			orgGrantInfoVOs = new ArrayList<GrantInfoVO>();
			for (OrgGrantInfoDTO orgGrantInfoDTO : orgGrantInfoDTOs.getOrgGrantInfoDTOs()) {
				GrantInfoVO orgGrantInfoVO = grant(orgGrantInfoDTO);
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
				if (orgGrantedItems == null || orgGrantedItems.getContent() == null
						|| orgGrantedItems.getContent().size() == 0)
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByUser(String userName) {
		// TODO Auto-generated method stub
		List<OrganizeUserGranted> organizeUserGranteds = queryGrantByUser(userName);
		if (organizeUserGranteds != null) {
			for (OrganizeUserGranted organizeUserGranted : organizeUserGranteds) {
				if (organizeUserGranted != null) {
					OrgGrantedUpdateInfo orgGrantedUpdateInfo = new OrgGrantedUpdateInfo();
					orgGrantedUpdateInfo.setId(organizeUserGranted.getId());
//					if(orgGrantedUpdateInfo.getAction()==null || orgGrantedUpdateInfo.getAction()==0 ||)
					iOrganizeService.deleteGranted(orgGrantedUpdateInfo);
				}
			}
		}
	}

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void updateByUser(String userName) {
//		// TODO Auto-generated method stub
//		List<OrganizeUserGranted> organizeUserGranteds=queryGrantByUser(userName);
//		if(organizeUserGranteds!=null) {
//			for(OrganizeUserGranted organizeUserGranted:organizeUserGranteds) {
//				if(organizeUserGranted!=null) {
//					OrgGrantedUpdateInfo orgGrantedUpdateInfo = new OrgGrantedUpdateInfo();
//					BeanUtils.copyProperties(organizeUserGranted, orgGrantedUpdateInfo);
////					if(orgGrantedUpdateInfo.getAction()==null || orgGrantedUpdateInfo.getAction()==0 ||)
//					iOrganizeService.updateGranted(orgGrantedUpdateInfo);
//				}
//			}
//		}	
//	}

//	private Page<ResoureQueryVO>  transToResVO(Page<OrganizeResourceDefined> organizeDefineds,List<OrganizeUserGranted> organizeUserGranteds,String orgCode) {
//		Page<ResoureQueryVO> resoureQueryVOs=null;
//		if (organizeDefineds != null&& orgCode!=null) {
//			String temOrgCode=orgCode;
//			List<ResoureQueryVO> resoureQueryVOls = new ArrayList<ResoureQueryVO>();
//			for (OrganizeResourceDefined organizeResourceDefineds : organizeDefineds.getContent()) {
//				ResoureQueryVO resoureQueryVO = new ResoureQueryVO();
//				BeanUtils.copyProperties(organizeResourceDefineds, resoureQueryVO);
//				if (organizeUserGranteds != null ) {
//					for (OrganizeUserGranted organizeUserGranted : organizeUserGranteds) {
//						if (resoureQueryVO.getId() == organizeUserGranted.getResource().getId()
//								&& orgCode.startsWith(organizeUserGranted.getOrgCode())) {
//							if (organizeUserGranted.getAction() > resoureQueryVO.getAction()) {
//								int action = organizeUserGranted.getAction();
//								resoureQueryVO.setActionArr(BinOrListUtil.transToBin(action));
//								resoureQueryVO.setGrantId(organizeUserGranted.getId());
//								temOrgCode=organizeUserGranted.getOrgCode();
////								resoureQueryVO.setOrgCode(organizeUserGranted.getOrgCode());
////								OrgSaveVO orgSaveVO = orgService
////										.getOrgNameByOrgCode(organizeUserGranted.getOrgCode());
////								if (orgSaveVO != null)
////									resoureQueryVO.setOrgName(orgSaveVO.getName());
//							}
//						}
//					}
//				}
//				resoureQueryVO.setOrgCode(temOrgCode);
//				OrgSaveVO orgSaveVO = orgService
//						.getOrgNameByOrgCode(temOrgCode);
//				if (orgSaveVO != null)
//					resoureQueryVO.setOrgName(orgSaveVO.getName());
//				resoureQueryVOls.add(resoureQueryVO);
//			}
//				resoureQueryVOs = new PageImpl<ResoureQueryVO>(resoureQueryVOls, organizeDefineds.getPageable(),
//						organizeDefineds.getTotalElements());
//			}
//		return resoureQueryVOs;
//	}
}
