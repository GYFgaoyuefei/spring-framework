package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.eseasky.core.framework.AuthService.exception.BusiException.BusiEnum;
import com.eseasky.core.framework.AuthService.exception.BusiException.BusiException;
import com.eseasky.core.framework.AuthService.module.model.AuthAccessToken;
import com.eseasky.core.framework.AuthService.module.model.ServLoginCode;
import com.google.common.base.Strings;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.repository.AuthAccessTokenRepository;
import com.eseasky.core.framework.AuthService.module.repository.LoginCodeRepository;
import com.eseasky.core.framework.AuthService.module.repository.ServUserInfoRepository;
import com.eseasky.core.framework.AuthService.module.service.GrantService;
import com.eseasky.core.framework.AuthService.module.service.GroupService;
import com.eseasky.core.framework.AuthService.module.service.PowerService;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.ServUserInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupInfo;
import com.eseasky.core.starters.organization.persistence.entity.PowerGroupQuery;
import com.eseasky.core.starters.organization.persistence.entity.VRInfo;
import com.eseasky.core.starters.organization.persistence.entity.dto.GrantByGroupDTO;
import com.eseasky.core.starters.organization.persistence.entity.dto.PowerGrantDTO;

import static com.eseasky.core.framework.AuthService.exception.BusiException.BusiEnum.NOT_FOUND_USER;

@Service
public class ServUserInfoServiceImpl implements ServUserInfoService {

	@Autowired
	private ServUserInfoRepository servUserInfoRepository;
	
	@Autowired
	private AuthAccessTokenRepository authAccessTokenRepository;

	@Autowired
	private LoginCodeRepository loginCodeRepository;

	@Autowired
	private IOrganizeService iOrganizeService;

	@Autowired
	private GrantService roleService;

	@Autowired
	private PowerService powerService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private OrgService orgService;

	@Override
	public ServUserInfo findByUserName(String userName) {
		// TODO Auto-generated method stub
		if (userName != null) {
			Optional<ServUserInfo> op = servUserInfoRepository.findByUserName(userName);
			if (op.isPresent()) {
				ServUserInfo userInfo = op.get();
				return userInfo;
			}
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfoVO updateServUserInfo(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO.getId() != null) {
			Optional<ServUserInfo> optional = servUserInfoRepository.findById(servUserInfoDTO.getId());
			if (optional.isPresent()) {
				ServUserInfo servUserInfo = optional.get();
				BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
				grantByGroups(servUserInfoDTO);
				servUserInfo = saveUserInfo(servUserInfo);
				if (servUserInfo != null) {
					servUserInfoVO = new ServUserInfoVO();
					BeanUtils.copyProperties(servUserInfo, servUserInfoVO);
				}
			} else {
				throw new BusiException(NOT_FOUND_USER);
			}
		} else {
			throw new BusiException(BusiEnum.USERINFO_NOID);
		}
		return servUserInfoVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfoVO deleteServUserInfoById(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO.getId() != null) {
			Optional<ServUserInfo> userInfo = servUserInfoRepository.findById(servUserInfoDTO.getId());
			if (userInfo.isPresent()) {
				OrgUserGranted orgUserGranted = iOrganizeService.getUserGranted(userInfo.get().getUserName());
				if (orgUserGranted != null && orgUserGranted.getGranteds() != null) {
					for (VRInfo vRInfo : orgUserGranted.getGranteds()) {
						VRInfoDTO vRInfoDTO = new VRInfoDTO();
						vRInfoDTO.setId(vRInfo.getId());
						powerService.reject(vRInfoDTO);
					}
				}
				roleService.deleteByUser(userInfo.get().getUserName());
				servUserInfoRepository.deleteById(userInfo.get().getId());
				servUserInfoVO = new ServUserInfoVO();
				BeanUtils.copyProperties(userInfo.get(), servUserInfoVO);
			} else {
				throw new BusiException(BusiEnum.NOT_FOUND_USER);
			}
		} else {
			throw new BusiException(BusiEnum.USERINFO_IDNOTNULL);
		}
		return servUserInfoVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfoVO addUserInfo(ServUserInfoDTO servUserInfoDTO) {
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO == null || servUserInfoDTO.getId() == null) {
			ServUserInfo servUserInfo = new ServUserInfo();
			BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
			servUserInfo=saveUserInfo(servUserInfo);
			grantBasePower(servUserInfoDTO);
			grantByGroups(servUserInfoDTO);				
			if (servUserInfo != null) {
				servUserInfoVO = new ServUserInfoVO();
				BeanUtils.copyProperties(servUserInfo, servUserInfoVO);
			}
			
		} else {
			throw new BusiException(BusiEnum.USERINFO_NOID);
		}
		return servUserInfoVO;
	}

	@Override
	public Page<ServUserInfo> queryUserInfo(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		Iterable<ServUserInfo> allUserInfo = servUserInfoRepository.findAll();
		for (ServUserInfo item : allUserInfo) {
			List<AuthAccessToken> AuthUser = authAccessTokenRepository.findByUserName(item.getUserName());
			if (AuthUser.size() == 0 || item.getState() == null) {
				item.setState("0");
			} else
				item.setState("1");
			servUserInfoRepository.save(item);
		}
		Pageable pageable = PageRequest.of(servUserInfoDTO.getPage(), servUserInfoDTO.getSize(),
				Sort.by(Direction.DESC, "id"));
		return servUserInfoRepository.findAll(new Specification<ServUserInfo>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<ServUserInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (!Strings.isNullOrEmpty(servUserInfoDTO.getOrgCode())) {
					predicates.add(criteriaBuilder.like(root.get("orgCode"), servUserInfoDTO.getOrgCode() + "%"));
				}
				if (!Strings.isNullOrEmpty(servUserInfoDTO.getNickName())) {
					predicates
							.add(criteriaBuilder.like(root.get("nickName"), "%" + servUserInfoDTO.getNickName() + "%"));
				}
				if (!Strings.isNullOrEmpty(servUserInfoDTO.getUserName())) {
					predicates
							.add(criteriaBuilder.like(root.get("userName"), "%" + servUserInfoDTO.getUserName() + "%"));
				}
				if (!Strings.isNullOrEmpty(servUserInfoDTO.getMobile())) {
					predicates.add(criteriaBuilder.like(root.get("mobile"), "%" + servUserInfoDTO.getMobile() + "%"));
				}
				if (servUserInfoDTO.getId() != null) {
					predicates.add(criteriaBuilder.equal(root.get("id"), servUserInfoDTO.getId()));
				}
				if (!Strings.isNullOrEmpty(servUserInfoDTO.getState())) {
					predicates.add(criteriaBuilder.equal(root.get("state"), servUserInfoDTO.getState()));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}

	@Override
	public ServUserInfoVO findById(ServUserInfoDTO servUserInfoDTO) {
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO.getId() != null) {
			ServUserInfo servUserInfo = new ServUserInfo();
			BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
			Optional<ServUserInfo> userInfo = servUserInfoRepository.findById(servUserInfo.getId());
			if (userInfo.isPresent() && userInfo.get() != null) {
				servUserInfoVO = new ServUserInfoVO();
				BeanUtils.copyProperties(userInfo.get(), servUserInfoVO);
				servUserInfoVO.setOrgName(orgService.getOrgNameByOrgCode(servUserInfoVO.getOrgCode()).getName());
				servUserInfoVO.setRoles(getUserGranted(servUserInfoVO.getUserName()));
				List<AuthAccessToken> authAccessToken = authAccessTokenRepository
						.findByUserName(userInfo.get().getUserName());
				if (authAccessToken == null)
					servUserInfoVO.setState("0");
				else
					servUserInfoVO.setState("1");
			}
		}
		return servUserInfoVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfoVO updatePwd(ServUserInfoDTO servUserInfoDTO) {
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO.getId() != null && servUserInfoDTO.getPassWord() != null) {
			ServUserInfo servUserInfo = new ServUserInfo();
			BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
			Optional<ServUserInfo> userInfo = servUserInfoRepository.findById(servUserInfo.getId());
			ServUserInfo saveInfo = null;
			if (userInfo.isPresent() && (saveInfo = userInfo.get()) != null) {
				saveInfo.setPassWord(servUserInfo.getPassWord());
				try {
					servUserInfoRepository.save(saveInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				servUserInfoVO = new ServUserInfoVO();
				BeanUtils.copyProperties(saveInfo, servUserInfoVO);
			}
		}
		return servUserInfoVO;
	}

	@Override
	public boolean CheckUsername(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> ServUserInfo = servUserInfoRepository.findByUserName(servUserInfoDTO.getUserName());
		if (ServUserInfo.isPresent())
			return true;
		else
			return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfoVO forceOffLine(ServUserInfoDTO servUserInfoDTO) {
		ServUserInfoVO servUserInfoVO = null;
		if (servUserInfoDTO.getUserName() != null && !"".equals(servUserInfoDTO.getUserName())) {
			List<AuthAccessToken> authUser = authAccessTokenRepository.findByUserName(servUserInfoDTO.getUserName());
			if (authUser.size() == 0)
				throw new BusiException(BusiEnum.USER_INVALID);
			else if (authUser.size() >= 1) {
				for (int i = 0; i < authUser.size(); i++) {
					authAccessTokenRepository.deleteById(authUser.get(i).getTokenId());
				}
			} else
				throw new BusiException(BusiEnum.NOTDELETE);

			Optional<ServUserInfo> userInfo = servUserInfoRepository.findByUserName(servUserInfoDTO.getUserName());
			if (userInfo.isPresent()) {
				ServUserInfo info = userInfo.get();
				info.setState("0");
				servUserInfoRepository.save(info);
				if (info != null) {
					servUserInfoVO = new ServUserInfoVO();
					BeanUtils.copyProperties(info, servUserInfoVO);
				}
			}
		} else {
			throw new BusiException(BusiEnum.USERINFO_IDNOTNULL);
		}
		return servUserInfoVO;
	}

	private List<OrgUserGranted> grantByGroups(ServUserInfoDTO servUserInfoDTO) {
		List<OrgUserGranted> orgUserGranteds = null;
		if (servUserInfoDTO != null && servUserInfoDTO.getGroupNames() != null
				&& servUserInfoDTO.getGroupNames().size() > 0) {
			for (String groupName : servUserInfoDTO.getGroupNames()) {
				if (Strings.isNullOrEmpty(groupName))
					continue;
				GrantByGroupDTO groupGrantDTO = new GrantByGroupDTO();
				groupGrantDTO.setAccount(servUserInfoDTO.getUserName());
				groupGrantDTO.setCreateAccount(servUserInfoDTO.getCreaterUser());
				groupGrantDTO.setGroupName(groupName);
				groupGrantDTO.setOrgCode(servUserInfoDTO.getOrgCode());
				try {
					OrgUserGranted orgUserGranted = iOrganizeService.grant(groupGrantDTO);
					if (orgUserGranted != null) {
						if (orgUserGranteds == null)
							orgUserGranteds = new ArrayList<OrgUserGranted>();
						orgUserGranteds.add(orgUserGranted);
					}
				} catch (Exception e) {
					throw new BusiException(BusiEnum.USERINFO_GROUPGRANT,e.getMessage());
				}
			}
		}
		return orgUserGranteds;

	}

//	private List<OrgUserGranted> grantGroups(ServUserInfoDTO servUserInfoDTO, boolean isUpdate) {
//		List<OrgUserGranted> orgUserGranteds = null;
//		if (servUserInfoDTO != null && servUserInfoDTO.getPowerIds() != null
//				&& servUserInfoDTO.getPowerIds().size() > 0) {
//			Set<Long> groupIds = null;
//			if (isUpdate) {
//				OrgUserGranted orgUserGranted = iOrganizeService.getUserGranted(servUserInfoDTO.getUserName());
//				if (orgUserGranted != null && orgUserGranted.getGranteds() != null) {
//					groupIds = orgUserGranted.getGranteds().stream().map(item -> item.getId())
//							.collect(Collectors.toSet());
//				}
//			}
//			for (Long groupId : servUserInfoDTO.getPowerIds()) {
//				if (groupIds != null && groupIds.contains(groupId)) {
//					groupIds.remove(groupId);
//					continue;
//				}
//				PowerGrantDTO userGrantByGroup = new PowerGrantDTO();
//				userGrantByGroup.setPowerId(groupId);
//				userGrantByGroup.setCreateUser(servUserInfoDTO.getCreaterUser());
//				userGrantByGroup.setOrgCode(servUserInfoDTO.getOrgCode());
//				userGrantByGroup.setUser(servUserInfoDTO.getUserName());
//				try {
//					OrgUserGranted orgUserGranted = iOrganizeService.grant(userGrantByGroup);
//					if (orgUserGranted != null) {
//						if (orgUserGranteds == null)
//							orgUserGranteds = new ArrayList<OrgUserGranted>();
//						orgUserGranteds.add(orgUserGranted);
//					}
//				} catch (Exception e) {
//					throw new BusiException(BusiEnum.USERINFO_GROUPGRANT);
//				}
//			}
//			if (groupIds != null && groupIds.size() > 0) {
//				groupIds.stream().forEach(item -> {
//					VRInfoDTO vRInfoDTO = new VRInfoDTO();
//					vRInfoDTO.setId(item);
//					powerService.reject(vRInfoDTO);
//				});
//			}
//		}
//		return orgUserGranteds;
//
//	}

	private void grantBasePower(ServUserInfoDTO servUserInfoDTO) {
		if(servUserInfoDTO!=null){
			PowerGroupQuery powerGroupQuery=new PowerGroupQuery();
			powerGroupQuery.setGroupName("基础权限");
			Page<PowerGroupInfo> groups = iOrganizeService.queryPower(powerGroupQuery);
			if (groups != null && groups.getContent() != null) {
				PowerGroupInfo group=groups.stream().filter(item -> item.getGroupName().equals("基础权限")).limit(1).collect(Collectors.toList()).get(0);
				if(group!=null) {
					PowerGrantDTO userGrantByGroup = new PowerGrantDTO();
					userGrantByGroup.setPowerId(group.getId());
					userGrantByGroup.setCreateUser(servUserInfoDTO.getCreaterUser());
					userGrantByGroup.setOrgCode(servUserInfoDTO.getOrgCode());
					userGrantByGroup.setUser(servUserInfoDTO.getUserName());
					try {
						iOrganizeService.grant(userGrantByGroup);
					} catch (Exception e) {
						throw new BusiException(BusiEnum.USERINFO_GROUPGRANT,e.getMessage());
					}
				}
			}
		}
	}
	
	@Override
	public ServUserInfo findByPhone(String phone) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> info = servUserInfoRepository.findByMobile(phone);
		if (info.isPresent()) {
			return info.get();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServUserInfo loginByCode(String phone, String code) {
		// TODO Auto-generated method stub
		ServLoginCode record = loginCodeRepository.findByPhone(phone);
		if (record != null && record.getCode().equals(code)) {
			loginCodeRepository.deleteById(record.getId());
			return findByPhone(phone);
		}
		return null;
	}

	@Override
	public void smsLoginCodeSend(String phone) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> user = servUserInfoRepository.findByMobile(phone);
		if (user.isPresent()) {
			// 生成code发送短信
		}
	}

	@Override
	public UserGrantInfoVO getUserGranted(String account) {
		// TODO Auto-generated method stub
		UserGrantInfoVO userGrantInfoVO = null;
		OrgUserGranted orgUserGranted = iOrganizeService.getUserGranted(account);
		userGrantInfoVO = groupService.transOUGToUGIVO(orgUserGranted);
		return userGrantInfoVO;
	}

	private ServUserInfo saveUserInfo(ServUserInfo servUserInfo) {
		if (servUserInfo != null) {
			try {
				servUserInfo = servUserInfoRepository.save(servUserInfo);
			} catch (DataIntegrityViolationException e) {
				if (e.getLocalizedMessage().contains("uniq_user_name")) {
					throw new BusiException(BusiEnum.USERNAME_REPEATABLE);
				}
				if (e.getLocalizedMessage().contains("serv_user_info_index_mobile")) {
					throw new BusiException(BusiEnum.MOBILE_REPEATABLE);
				}
			}
		}
		return servUserInfo;
	}
}
