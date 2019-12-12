package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eseasky.core.framework.AuthService.exception.BusiException.BusiEnum;
import com.eseasky.core.framework.AuthService.exception.BusiException.BusiException;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.MulOrgsVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgInsertInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrganizeQuery;
import com.eseasky.core.starters.organization.persistence.entity.OrganizeUpdateInfo;
import com.eseasky.core.starters.organization.persistence.model.OrganizeDefined;
import com.eseasky.global.utils.SequeceHelper;
import com.google.common.base.Strings;

@Service
public class OrgServiceImpl implements OrgService {
	@Autowired
	private IOrganizeService iOrganizeService;

	@Override
	public Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		Page<OrgQueryVO> orgQueryVOs = null;
		if (orgQueryDTO != null) {
			OrganizeQuery organizeQuery = new OrganizeQuery();
			BeanUtils.copyProperties(orgQueryDTO, organizeQuery);
			if (orgQueryDTO.getSize() != 0)
				organizeQuery.setPageSize(orgQueryDTO.getSize());
			Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
			if (organizeDefineds != null) {
				List<OrgQueryVO> orgQueryVOls = organizeDefineds.stream().map(item -> {
					OrgQueryVO orgQueryVO = new OrgQueryVO();
					BeanUtils.copyProperties(item, orgQueryVO);
					return orgQueryVO;
				}).collect(Collectors.toList());
				orgQueryVOs = new PageImpl<OrgQueryVO>(orgQueryVOls, organizeDefineds.getPageable(),
						organizeDefineds.getTotalElements());
			}
		}
		return orgQueryVOs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		OrgSaveVO orgSaveVO = null;
		if (orgSaveDTO != null) {
			if(orgSaveDTO.getLevel()!=3) {
				orgSaveVO=checkOrgName(orgSaveDTO);
				if(orgSaveVO!=null && orgSaveVO.getId()!=null)
					throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
			}
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
	@Transactional(rollbackFor = Exception.class)
	public OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = null;
		if (orgUpdateDTO != null) {
			if(orgUpdateDTO.getName()!=null && orgUpdateDTO.getLevel()!=null && orgUpdateDTO.getLevel()!=3) {
				OrgSaveDTO orgSaveDTO=new OrgSaveDTO ();
				BeanUtils.copyProperties(orgUpdateDTO, orgSaveDTO);
				orgSaveVO=checkOrgName(orgSaveDTO);
				if(orgSaveVO!=null && orgSaveVO.getId()!=null && orgUpdateDTO.getId()!=orgSaveVO.getId())
					throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
			}
			OrganizeUpdateInfo orgInsertInfo = new OrganizeUpdateInfo();
			BeanUtils.copyProperties(orgUpdateDTO, orgInsertInfo);
			OrganizeDefined organizeDefined = iOrganizeService.updateOrganize(orgInsertInfo);
			if (organizeDefined != null) {
				orgSaveVO = new OrgSaveVO();
				BeanUtils.copyProperties(organizeDefined, orgSaveVO);
			}
		}
		return orgSaveVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrgSaveVO disableOrg(OrgUpdateDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = null;
		if (orgUpdateDTO != null && orgUpdateDTO.getId() != null) {
			OrganizeDefined organizeDefined = iOrganizeService.disableOrganize(orgUpdateDTO.getId());
			if (organizeDefined != null) {
				orgSaveVO = new OrgSaveVO();
				BeanUtils.copyProperties(organizeDefined, orgSaveVO);
			}
		}
		return orgSaveVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrgSaveVO openOrg(OrgUpdateDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = null;
		if (orgUpdateDTO != null && orgUpdateDTO.getId() != null) {
			OrganizeDefined organizeDefined = iOrganizeService.openOrganize(orgUpdateDTO.getId());
			if (organizeDefined != null) {
				orgSaveVO = new OrgSaveVO();
				BeanUtils.copyProperties(organizeDefined, orgSaveVO);
			}
		}
		return orgSaveVO;
	}

	@Override
	public OrgSaveVO getOrgNameByOrgCode(String orgCode) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = null;
		if (orgCode != null) {
			int level = SequeceHelper.getLevel(orgCode);
			orgSaveVO = new OrgSaveVO();
			orgSaveVO.setLevel(level);
			int length = 0;
			for (int i = 0; i < level; i++) {
				length = length + SequeceHelper.SEQUECE_LEVEL_LENGTH[i];
				OrganizeQuery organizeQuery = new OrganizeQuery();
				organizeQuery.setOrgCode(orgCode.substring(0, length));
				Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
				if (organizeDefineds != null && organizeDefineds.getContent() != null) {
					String orgName = orgSaveVO.getName() == null ? organizeDefineds.getContent().get(0).getName()
							: orgSaveVO.getName() + ">" + organizeDefineds.getContent().get(0).getName();
					orgSaveVO.setName(orgName);
				}
			}
		}
		return orgSaveVO;
	}

	@Override
	public List<MulOrgsVO> queryMulOrgs() {
		// TODO Auto-generated method stub
		List<MulOrgsVO> mulOrgsVOs=null;
		List<OrgQueryVO> organizeDefineds = null;
		int page = 0;
		while (true) {
			OrgQueryDTO orgQueryDTO=new OrgQueryDTO();
			orgQueryDTO.setLevel(2);
			orgQueryDTO.setPage(page);
			orgQueryDTO.setSize(50);
			Page<OrgQueryVO> orgQueryVOs = queryOrg(orgQueryDTO);
			if (orgQueryVOs == null || orgQueryVOs.getContent() == null
					|| orgQueryVOs.getContent().size() == 0)
				break;
			if (organizeDefineds == null)
				organizeDefineds = orgQueryVOs.getContent();
			else
				organizeDefineds.addAll(orgQueryVOs.getContent());
			page++;
		}
		if(organizeDefineds!=null) {
			Map<String, List<OrgQueryVO>> mapOrgsVO=organizeDefineds.stream().collect(Collectors.groupingBy(OrgQueryVO::getParentOrgCode));
			if(mapOrgsVO!=null) {
				MulOrgsVO mulOrgsVO=null;
				for(Entry<String, List<OrgQueryVO>> entry:mapOrgsVO.entrySet()) {
					OrgQueryDTO orgQueryDTO=new OrgQueryDTO();
					orgQueryDTO.setLevel(1);
					orgQueryDTO.setOrgCode(entry.getKey());
					Page<OrgQueryVO> orgQueryVOs = queryOrg(orgQueryDTO);
					if(orgQueryVOs!=null && orgQueryVOs.getNumberOfElements()>0) {
						OrgQueryVO levelFirOrg=orgQueryVOs.getContent().get(0);
						mulOrgsVO=new MulOrgsVO();
						mulOrgsVO.setLevelFirOrg(levelFirOrg);
						mulOrgsVO.setLevelSecOrgs(entry.getValue());
						if(mulOrgsVOs==null)
							mulOrgsVOs=new ArrayList<MulOrgsVO>(); 
						mulOrgsVOs.add(mulOrgsVO);					
					}
				}
			}
		}
		return mulOrgsVOs;
	}
	
	@Override
	public OrgSaveVO checkOrgName(OrgSaveDTO orgSaveDTO) {
		OrgSaveVO orgSaveVO = null;
		if (orgSaveDTO != null) {
			OrganizeQuery organizeQuery = new OrganizeQuery();
			BeanUtils.copyProperties(orgSaveDTO, organizeQuery);	
			if(Strings.isNullOrEmpty(orgSaveDTO.getParentOrgCode())&&orgSaveDTO.getLevel()==null)
				organizeQuery.setLevel(1);
			organizeQuery.setPageSize(50);
			organizeQuery.setKeyWords(orgSaveDTO.getName());
			Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
			if (organizeDefineds != null && organizeDefineds.getContent()!=null && organizeDefineds.getContent().size()>0) {
				List<OrgSaveVO> orgSaveVOs=organizeDefineds.stream().filter(item->orgSaveDTO.getName().equals(item.getName())).map(item->{
					OrgSaveVO temOrgSaveVO=new OrgSaveVO();
					BeanUtils.copyProperties(item,temOrgSaveVO);
					return temOrgSaveVO;
				}).collect(Collectors.toList());
				if(orgSaveVOs!=null && orgSaveVOs.size()>0) {
					orgSaveVO = orgSaveVOs.get(0);
				}				
			}
		}
		return orgSaveVO;
	}
}
