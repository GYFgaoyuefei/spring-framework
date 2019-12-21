package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eseasky.core.framework.AuthService.module.service.GroupService;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.QueryGroupDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGraItemForUserGroupVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.VRInfoVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedItemForUserGroup;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.VRInfo;
import com.eseasky.core.starters.organization.persistence.entity.dto.GrantByGroupDTO;
import com.eseasky.core.starters.organization.persistence.entity.dto.GroupAddDTO;
import com.eseasky.core.starters.organization.persistence.entity.dto.GroupQueryDTO;
import com.eseasky.core.starters.organization.persistence.entity.vo.GroupVO;


@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	private IOrganizeService iOrganizeService;

	@Autowired
	private OrgService orgService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public GroupSaveVO addGroup(GroupSaveDTO groupSaveDTO) {
		// TODO Auto-generated method stub
		GroupSaveVO groupSaveVO=null;
		if(groupSaveDTO!=null) {
			GroupAddDTO groupAddDTO=new GroupAddDTO();
			BeanUtils.copyProperties(groupSaveDTO, groupAddDTO);
			GroupVO groupVO=iOrganizeService.addGroup(groupAddDTO);
			if(groupVO!=null) {
				groupSaveVO=new GroupSaveVO();
				BeanUtils.copyProperties(groupVO, groupSaveVO);
			}				
		}
		return groupSaveVO;
	}

	@Override
	public List<GroupQueryVO> queryGroup(QueryGroupDTO groupQueryDTO) {
		// TODO Auto-generated method stub
		List<GroupQueryVO> groupQueryVOs=null;
		if(groupQueryDTO!=null) {
			GroupQueryDTO groupQuery=new GroupQueryDTO();
			BeanUtils.copyProperties(groupQueryDTO, groupQuery);
			List<GroupVO> groupVOs = iOrganizeService.queryGroup(groupQuery);
			if(groupVOs!=null && groupVOs.size()>0 ) {
				groupQueryVOs= groupVOs.stream().map(item->{
					GroupQueryVO groupQueryVO=new GroupQueryVO();
					BeanUtils.copyProperties(item, groupQueryVO);
					return groupQueryVO;

				}).collect(Collectors.toList());
			}
		}
		return groupQueryVOs;
	}

	@Override
	public void deleteGroup(QueryGroupDTO groupUpdateDTO) {
		// TODO Auto-generated method stub
		if(groupUpdateDTO!=null && groupUpdateDTO.getId()!=null) {
			iOrganizeService.deletePowerGroup(groupUpdateDTO.getId());
		}	
	}

	@Override
	public UserGrantInfoVO grantByGroup(GroupGrantDTO groupGrantDTO) {
		// TODO Auto-generated method stub
		UserGrantInfoVO userGrantInfoVO=null;
		if(groupGrantDTO!=null) {
			GrantByGroupDTO grantByGroupDTO=new GrantByGroupDTO();
			BeanUtils.copyProperties(groupGrantDTO, grantByGroupDTO);
			OrgUserGranted orgUserGranted=iOrganizeService.grant(grantByGroupDTO);
			userGrantInfoVO=transOUGToUGIVO(orgUserGranted);
		}
		return userGrantInfoVO;
	}

	@Override
	public UserGrantInfoVO transOUGToUGIVO(OrgUserGranted orgUserGranted) {
		UserGrantInfoVO userGrantInfoVO =null;
		if(orgUserGranted!=null) {
			userGrantInfoVO=new UserGrantInfoVO();
			userGrantInfoVO.setUser(orgUserGranted.getUser());
			if(orgUserGranted.getGranteds()!=null && orgUserGranted.getGranteds().size()>0) {
				List<VRInfoVO> granteds=new ArrayList<VRInfoVO>();
				for(VRInfo vRInfo:orgUserGranted.getGranteds()) {
					VRInfoVO vRInfoVO=new VRInfoVO();
					BeanUtils.copyProperties(vRInfo, vRInfoVO);
					vRInfoVO.setOrgName(orgService.getOrgNameByOrgCode(vRInfoVO.getOrgCode()).getName());
					granteds.add(vRInfoVO);
				}
				userGrantInfoVO.setGranteds(granteds);
			}
			if(orgUserGranted.getExtraGranteds()!=null && orgUserGranted.getExtraGranteds().size()>0) {
				List<OrgGraItemForUserGroupVO> extraGranteds=new ArrayList<OrgGraItemForUserGroupVO>();
				for(OrgGrantedItemForUserGroup orgGrantedItemForUserGroup:orgUserGranted.getExtraGranteds()) {
					OrgGraItemForUserGroupVO orgGraItemForUserGroupVO=new OrgGraItemForUserGroupVO();
					BeanUtils.copyProperties(orgGrantedItemForUserGroup, orgGraItemForUserGroupVO);
					orgGraItemForUserGroupVO.setOrgName(orgService.getOrgNameByOrgCode(orgGraItemForUserGroupVO.getOrgCode()).getName());
					extraGranteds.add(orgGraItemForUserGroupVO);
				}
				userGrantInfoVO.setExtraGranteds(extraGranteds);
			}
		}
		return userGrantInfoVO;
		
	}
	
}
