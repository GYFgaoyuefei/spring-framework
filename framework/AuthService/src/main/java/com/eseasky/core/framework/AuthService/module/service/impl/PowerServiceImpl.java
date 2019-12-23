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

import com.eseasky.core.framework.AuthService.exception.BusiException.BusiEnum;
import com.eseasky.core.framework.AuthService.exception.BusiException.BusiException;
import com.eseasky.core.framework.AuthService.module.service.PowerService;
import com.eseasky.core.framework.AuthService.protocol.dto.AuPowerGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerGroupCreateItemDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.VRInfoVO;
import com.eseasky.core.framework.AuthService.utils.BinOrListUtil;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupCreate;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupCreateItem;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupGetItem;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupInfo;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupQuery;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupUpdate;
import com.eseasky.core.starters.organization.persistence.entity.ResourceQuery;
import com.eseasky.core.starters.organization.persistence.entity.dto.PowerGrantDTO;
import com.eseasky.core.starters.organization.persistence.model.OrgPowerDefined;
import com.eseasky.core.starters.organization.persistence.model.OrganizeResourceDefined;
import com.google.common.base.Strings;


@Service
public class PowerServiceImpl implements PowerService{

	@Autowired
	private IOrganizeService iOrganizeService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PowerSaveVO createPower(PowerSaveDTO groupSaveDTO) {
		// TODO Auto-generated method stub
		PowerSaveVO groupSaveVO=null;
		if(groupSaveDTO!=null) {
			PowerGroupCreate powerGroupCreate=new PowerGroupCreate();
			BeanUtils.copyProperties(groupSaveDTO, powerGroupCreate);
			if(groupSaveDTO.getItems()!=null) {
				powerGroupCreate.setItems(transPowDtoToPow(groupSaveDTO.getItems()));
			}
			OrgPowerDefined orgPowerGroup=iOrganizeService.createPower(powerGroupCreate);
			if(orgPowerGroup!=null) {
				groupSaveVO=new PowerSaveVO();
				BeanUtils.copyProperties(orgPowerGroup, groupSaveVO);
			}				
		}
		return groupSaveVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PowerSaveVO updatePower(PowerUpdateDTO groupUpdateDTO) {
		// TODO Auto-generated method stub
		PowerSaveVO groupSaveVO=null;
		if(groupUpdateDTO!=null) {
			PowerGroupUpdate powerGroupUpdate=new PowerGroupUpdate();
			BeanUtils.copyProperties(groupUpdateDTO, powerGroupUpdate);
			if(groupUpdateDTO.getItems()!=null) {
				powerGroupUpdate.setItems(transPowDtoToPow(groupUpdateDTO.getItems()));
			}
			OrgPowerDefined orgPowerGroup=iOrganizeService.updatePower(powerGroupUpdate);
			if(orgPowerGroup!=null) {
				groupSaveVO=new PowerSaveVO();
				BeanUtils.copyProperties(orgPowerGroup, groupSaveVO);
			}				
		}
		return groupSaveVO;
	}

	private List<PowerGroupCreateItem> transPowDtoToPow(List<PowerGroupCreateItemDTO> items) {
		// TODO Auto-generated method stub
		List<PowerGroupCreateItem> powerGroupCreateItems=items.stream().filter(item->BinOrListUtil.transToInt(item.getAction())!=0).map(item->{
			PowerGroupCreateItem powerGroupCreateItem=new PowerGroupCreateItem();
			BeanUtils.copyProperties(item, powerGroupCreateItem);
			int action=BinOrListUtil.transToInt(item.getAction());
			powerGroupCreateItem.setAction(action);			
			return powerGroupCreateItem;
		}).collect(Collectors.toList());
		return powerGroupCreateItems;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PowerSaveVO deletePower(PowerUpdateDTO groupUpdateDTO) {
		// TODO Auto-generated method stub
		PowerSaveVO groupSaveVO=null;
		if(groupUpdateDTO!=null && groupUpdateDTO.getGroupId()!=null) {
			iOrganizeService.deletePower(groupUpdateDTO.getGroupId());				
		}else {
			throw new BusiException(BusiEnum.GROUP_NOID);
		}
		return groupSaveVO;
	}

	@Override
	public Page<PowerQueryVO> queryPower(PowerQueryDTO groupQueryDTO) {
		// TODO Auto-generated method stub
		Page<PowerQueryVO> groupQueryVOs=null;
		if(groupQueryDTO!=null) {
			PowerGroupQuery powerGroupQuery=new PowerGroupQuery();
			BeanUtils.copyProperties(groupQueryDTO, powerGroupQuery);
			if(groupQueryDTO.getSize()!=0)
				powerGroupQuery.setPageSize(groupQueryDTO.getSize());
			if(!Strings.isNullOrEmpty(groupQueryDTO.getPowerName()))
				powerGroupQuery.setGroupName("%"+groupQueryDTO.getPowerName());
			Page<PowerGroupInfo> powerGroupInfos=iOrganizeService.queryPower(powerGroupQuery);
			if(powerGroupInfos!=null) {
				List<PowerQueryVO> orgQueryVOls=powerGroupInfos.stream().map(item->{
					PowerQueryVO orgQueryVO=new PowerQueryVO();
					BeanUtils.copyProperties(item, orgQueryVO);
					return orgQueryVO;
				}).collect(Collectors.toList());
				groupQueryVOs=new PageImpl<PowerQueryVO>(orgQueryVOls,powerGroupInfos.getPageable(),powerGroupInfos.getTotalElements());
			}
		}
		return groupQueryVOs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrgGrantInfoVO grant(AuPowerGrantDTO groupGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO=null;
		if(groupGrantDTO!=null) {
			if(groupGrantDTO.getUserVRId()!=null) {
				iOrganizeService.reject(groupGrantDTO.getUserVRId());
			}
			PowerGrantDTO userGrantByGroup=new PowerGrantDTO();
			BeanUtils.copyProperties(groupGrantDTO, userGrantByGroup);
			OrgUserGranted orgUserGranted=iOrganizeService.grant(userGrantByGroup);
			if(orgUserGranted!=null) {
				orgGrantInfoVO=new OrgGrantInfoVO();
				BeanUtils.copyProperties(orgUserGranted, orgGrantInfoVO);
			}
		}
		
		return orgGrantInfoVO;
	}

	@Override
	public Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> resoureQueryVOs = null;
		if (resoureQueryDTO != null) {
			ResourceQuery resourceQuery = new ResourceQuery();
			BeanUtils.copyProperties(resoureQueryDTO, resourceQuery);
			resoureQueryDTO.setSize(50);
			if (resoureQueryDTO.getSize() != 0)
				resourceQuery.setPageSize(resoureQueryDTO.getSize());
			List<PowerGroupGetItem> powerGroupGetItems = null;
			Page<OrganizeResourceDefined> organizeDefineds = iOrganizeService.getResourceItems(resourceQuery);
			if (resoureQueryDTO.getGroupId() != null) {
				powerGroupGetItems=getItemByGruopId(resoureQueryDTO.getGroupId());
			}
			resoureQueryVOs=transToResVO(organizeDefineds, powerGroupGetItems);
		}
		return resoureQueryVOs;
	}

	private Page<ResoureQueryVO> transToResVO(Page<OrganizeResourceDefined> organizeDefineds,
			List<PowerGroupGetItem> powerGroupGetItems) {
		// TODO Auto-generated method stub
		Page<ResoureQueryVO> resoureQueryVOs=null;
		if (organizeDefineds != null) {
			List<ResoureQueryVO> resoureQueryVOls = new ArrayList<ResoureQueryVO>();
			for (OrganizeResourceDefined organizeResourceDefineds : organizeDefineds.getContent()) {
				ResoureQueryVO resoureQueryVO = new ResoureQueryVO();
				BeanUtils.copyProperties(organizeResourceDefineds, resoureQueryVO);
				if (powerGroupGetItems != null ) {
					for (PowerGroupGetItem powerGroupGetItem : powerGroupGetItems) {
						if (resoureQueryVO.getId() == powerGroupGetItem.getResId()) {						
								int action = powerGroupGetItem.getAction();
								resoureQueryVO.setActionArr(BinOrListUtil.transToBin(action));
								resoureQueryVO.setGroupItemId(powerGroupGetItem.getId());
						}
					}
				}
				resoureQueryVO.setOrgCode(null);
				resoureQueryVOls.add(resoureQueryVO);
			}
				resoureQueryVOs = new PageImpl<ResoureQueryVO>(resoureQueryVOls, organizeDefineds.getPageable(),
						organizeDefineds.getTotalElements());
			}
		return resoureQueryVOs;
	}


	private List<PowerGroupGetItem> getItemByGruopId(Long groupId) {
		// TODO Auto-generated method stub
		List<PowerGroupGetItem> powerGroupGetItems = null;
		if (groupId != null) {		
			int page = 0;
			while (true) {
				Page<PowerGroupGetItem> temPowerGroupGetItem = iOrganizeService.getGroupPowers(groupId,page,50);
				if (temPowerGroupGetItem == null || temPowerGroupGetItem.getContent() == null ||temPowerGroupGetItem.getContent().size()==0)
					break;
				if (powerGroupGetItems == null)
					powerGroupGetItems = temPowerGroupGetItem.getContent();
				else
					powerGroupGetItems.addAll(temPowerGroupGetItem.getContent());
				page++;
			}
		}
		return powerGroupGetItems;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public VRInfoVO reject(VRInfoDTO vRInfoDTO) {
		// TODO Auto-generated method stub
		if(vRInfoDTO!=null && vRInfoDTO.getId()!=null) {
			iOrganizeService.reject(vRInfoDTO.getId());
		}
		return null;
	}

	
	
}
