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
import com.eseasky.core.framework.AuthService.module.service.GroupService;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerGroupCreateItemDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
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
import com.eseasky.core.starters.organization.persistence.entity.UserGrantByGroup;
import com.eseasky.core.starters.organization.persistence.model.OrgPowerGroup;
import com.eseasky.core.starters.organization.persistence.model.OrganizeResourceDefined;
import com.google.common.base.Strings;


@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	private IOrganizeService iOrganizeService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public GroupSaveVO saveGroup(GroupSaveDTO groupSaveDTO) {
		// TODO Auto-generated method stub
		GroupSaveVO groupSaveVO=null;
		if(groupSaveDTO!=null) {
			PowerGroupCreate powerGroupCreate=new PowerGroupCreate();
			BeanUtils.copyProperties(groupSaveDTO, powerGroupCreate);
			if(groupSaveDTO.getItems()!=null) {
				powerGroupCreate.setItems(transPowDtoToPow(groupSaveDTO.getItems()));
			}
			OrgPowerGroup orgPowerGroup=iOrganizeService.createGroup(powerGroupCreate);
			if(orgPowerGroup!=null) {
				groupSaveVO=new GroupSaveVO();
				BeanUtils.copyProperties(orgPowerGroup, groupSaveVO);
			}				
		}
		return groupSaveVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public GroupSaveVO updateGroup(GroupUpdateDTO groupUpdateDTO) {
		// TODO Auto-generated method stub
		GroupSaveVO groupSaveVO=null;
		if(groupUpdateDTO!=null) {
			PowerGroupUpdate powerGroupUpdate=new PowerGroupUpdate();
			BeanUtils.copyProperties(groupUpdateDTO, powerGroupUpdate);
			if(groupUpdateDTO.getItems()!=null) {
				powerGroupUpdate.setItems(transPowDtoToPow(groupUpdateDTO.getItems()));
			}
			OrgPowerGroup orgPowerGroup=iOrganizeService.updateGroup(powerGroupUpdate);
			if(orgPowerGroup!=null) {
				groupSaveVO=new GroupSaveVO();
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
	public GroupSaveVO deleteGroup(GroupUpdateDTO groupUpdateDTO) {
		// TODO Auto-generated method stub
		GroupSaveVO groupSaveVO=null;
		if(groupUpdateDTO!=null && groupUpdateDTO.getGroupId()!=null) {
			iOrganizeService.deleteGroup(groupUpdateDTO.getGroupId());				
		}else {
			throw new BusiException(BusiEnum.GROUP_NOID);
		}
		return groupSaveVO;
	}

	@Override
	public Page<GroupQueryVO> queryGroup(GroupQueryDTO groupQueryDTO) {
		// TODO Auto-generated method stub
		Page<GroupQueryVO> groupQueryVOs=null;
		if(groupQueryDTO!=null) {
			PowerGroupQuery powerGroupQuery=new PowerGroupQuery();
			BeanUtils.copyProperties(groupQueryDTO, powerGroupQuery);
			if(groupQueryDTO.getSize()!=0)
				powerGroupQuery.setPageSize(groupQueryDTO.getSize());
			if(!Strings.isNullOrEmpty(groupQueryDTO.getGroupName()))
				powerGroupQuery.setGroupName("%"+groupQueryDTO.getGroupName());
			Page<PowerGroupInfo> powerGroupInfos=iOrganizeService.queryGroup(powerGroupQuery);
			if(powerGroupInfos!=null) {
				List<GroupQueryVO> orgQueryVOls=powerGroupInfos.stream().map(item->{
					GroupQueryVO orgQueryVO=new GroupQueryVO();
					BeanUtils.copyProperties(item, orgQueryVO);
					return orgQueryVO;
				}).collect(Collectors.toList());
				groupQueryVOs=new PageImpl<GroupQueryVO>(orgQueryVOls,powerGroupInfos.getPageable(),powerGroupInfos.getTotalElements());
			}
		}
		return groupQueryVOs;
	}

	@Override
	public OrgGrantInfoVO grant(GroupGrantDTO groupGrantDTO) {
		// TODO Auto-generated method stub
		OrgGrantInfoVO orgGrantInfoVO=null;
		if(groupGrantDTO!=null) {
			UserGrantByGroup userGrantByGroup=new UserGrantByGroup();
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

	
	
}
