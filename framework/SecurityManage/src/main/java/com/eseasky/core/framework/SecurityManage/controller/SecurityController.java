package com.eseasky.core.framework.SecurityManage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eseasky.core.framework.SecurityManage.protocol.dto.PowerDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.RoleDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.UserDTO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.ApiVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.ElementVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.GroupApisVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.MenuTreeVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.MenuVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.PowerTreeVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.PowerVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.ResourcesVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.RoleVO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.UserVO;
import com.eseasky.core.framework.SecurityManage.service.SecurityService;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.MsgReturn;
import com.eseasky.global.utils.CheckUtils;
import com.eseasky.starter.security.core.SecurityConstants;
import com.eseasky.starter.security.module.model.Api;
import com.eseasky.starter.security.module.model.Menu;
import com.eseasky.starter.security.module.model.Power;
import com.eseasky.starter.security.module.model.Resources;
import com.eseasky.starter.security.module.model.Role;
import com.eseasky.starter.security.module.model.User;
import com.eseasky.starter.security.module.model.enums.NormalStatus;
import com.eseasky.starter.security.module.model.enums.ResourceType;

import io.swagger.annotations.ApiOperation;




@RestController
public class SecurityController {
	@Autowired
	SecurityService securityService;
	
	@ApiOperation(value="组合查询用户信息")
	@PostMapping(value = "/security/queryUser", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<UserVO>>> queryUser(@RequestBody UserDTO userDTO) {
		MsgReturn<List<UserVO>> msgReturn = new MsgReturn<List<UserVO>>();
		List<User> userList = securityService.queryUser(userDTO, userDTO.getPage(), 10000);
		List<UserVO> userVO = new ArrayList<>();
		for (User user : userList) {
			UserVO uVO = tranUserTOUserVO(user);
			List<String> role_name = new ArrayList<>();
			List<String> key = new ArrayList<>();
			for (Role role : user.getRoles()) {
				role_name.add(role.getName());
				Set<Power> powers = role.getPowers();
				if (powers.size() > 0) {
					List<String> ids = powers.stream().map(Power::getResources).map(Resources::getId).map(item -> {
						return item.toString();
					}).collect(Collectors.toList());
					key.addAll(ids);
				}
			}
			key = key.stream().distinct().collect(Collectors.toList());
			uVO.setKey(key);
			uVO.setRole_name(role_name);
			userVO.add(uVO);
		}
		msgReturn.success().setData(userVO);
		return new ResponseEntity<MsgReturn<List<UserVO>>>(msgReturn, HttpStatus.OK);
	}


	@ApiOperation(value="组合查询用户信息")
	@PostMapping(value = "/security/queryOnlyUser", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<UserVO>>> queryOnlyUser(@RequestBody UserDTO userDTO) {
		MsgReturn<List<UserVO>> msgReturn = new MsgReturn<List<UserVO>>();
		List<User> userList = securityService.queryOnlyUser();
		List<UserVO> userVO = userList.stream().map(item -> {return tranUserTOUserVO(item);}).collect(Collectors.toList());
		msgReturn.success().setData(userVO);
		return new ResponseEntity<MsgReturn<List<UserVO>>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="组合查询权限信息")
	@PostMapping(value = "/security/queryPower", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<PowerVO>>> queryPower(@RequestBody PowerDTO powerDTO) {
		MsgReturn<List<PowerVO>> msgReturn = new MsgReturn<List<PowerVO>>();
		List<Power> power = securityService.queryPower(powerDTO, powerDTO.getPage(), powerDTO.getPageSize());
		List<PowerVO> PowerVO = power.stream().map(item -> {
			return tranPowerTOPowerVO(item);
		}).collect(Collectors.toList());
		msgReturn.success().setData(PowerVO);
		return new ResponseEntity<MsgReturn<List<PowerVO>>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="组合查询资源信息")
	@PostMapping(value = "/security/queryAllResources")
	public ResponseEntity<MsgReturn<PowerTreeVO>> queryResources(@RequestBody PowerDTO powerDTO) {
		MsgReturn<PowerTreeVO> msgReturn = new MsgReturn<PowerTreeVO>();
		List<GroupApisVO> groupApisVOs = new ArrayList<>();
		List<String> key = new ArrayList<>();
		List<String> apiKey = new ArrayList<>();
		List<String> ApiKey = new ArrayList<>();
		Resources resources = new Resources();
		resources.setStatus(null);
		List<MenuTreeVO> MenuTrees = securityService.queryResourcesByName(resources, powerDTO.getPage(), 10000);
		RoleVO roleVO = new RoleVO();
		List<ResourcesVO> resourceApiVOs = securityService.queryAllResources()
				.stream().filter(item -> NormalStatus.isValid(item.getStatus()))
				.map(item -> {
			return tranResourcesTOResourcesVO(item);
		}).collect(Collectors.toList());
		List<String> groups = resourceApiVOs.stream().map(ResourcesVO::getGroup).distinct()
				.collect(Collectors.toList());
		for (String group : groups) {
			GroupApisVO groupApisVO = new GroupApisVO();
			List<ApiVO> apiVOs = resourceApiVOs.stream().filter(b -> b.getGroup().equals(group))
					.filter(c -> c.getApi() != null && c.getElement() == null).map(item -> {
						item.getApi().setId(item.getId());
						item.getApi().setKey(item.getId());
						item.getApi().setTitle(item.getName());
						item.getApi().setServerName(item.getName());
						return item;
					}).map(ResourcesVO::getApi).distinct().collect(Collectors.toList());
			if (!CheckUtils.isEmpty(apiVOs)) {
				groupApisVO.setTitle(group);
				groupApisVO.setKey(group);
				groupApisVO.setChildren(apiVOs);
				groupApisVOs.add(groupApisVO);
				apiKey.addAll(apiVOs.stream().map(ApiVO::getId).map(item -> {
					return item.toString();
				}).collect(Collectors.toList()));
			}
		}
		if (powerDTO.getId() != null) {
			Role role = securityService.queryRoleById(powerDTO.getId());
			roleVO = tranRoleTORoleVO(role);
			Set<Power> powers = role.getPowers();
			if (powers.size() > 0) {
				key = powers.stream().map(Power::getResources).filter(a -> a.getMenu() != null).map(a -> {
					if (a.getElement() == null) {
						List<Power> powerList = securityService.queryPowerByMenuParentId(a.getMenu().getId())
								.stream().filter(item -> NormalStatus.isValid(item.getStatus())).collect(Collectors.toList());
						powerList.retainAll(powers);
						if (powerList.size() > 0) {
							return null;
						}
					}
					return a;
				}).filter(a -> a != null).map(Resources::getId).map(item -> {
					return item.toString();
				}).collect(Collectors.toList());
				List<String> apiIds = powers.stream().map(Power::getResources)
						.filter(a -> a.getApi() != null && a.getElement() == null).map(Resources::getId).map(item -> {
							return item.toString();
						}).collect(Collectors.toList());
//				ApiKey = apiKey.stream().distinct().filter(a -> apiIds.contains(a)).collect(Collectors.toList());
				ApiKey.addAll(apiIds);
			}
		}
		List<Long> ids = securityService.queryAllResources().stream()
				.filter(item -> NormalStatus.isValid(item.getStatus())).map(Resources::getId)
				.collect(Collectors.toList());
		PowerTreeVO powerTreeVO = new PowerTreeVO();
		powerTreeVO.setRoleVO(roleVO);
		powerTreeVO.setMenuTreeVOs(MenuTrees);
		powerTreeVO.setKey(key);
		powerTreeVO.setApiKey(ApiKey);
		powerTreeVO.setIds(ids);
		powerTreeVO.setGroupApisVOs(groupApisVOs);
		msgReturn.success().setData(powerTreeVO);
		return new ResponseEntity<MsgReturn<PowerTreeVO>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="组合查询角色信息")
	@PostMapping(value = "/security/queryRole", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<RoleVO>>> queryRole(@RequestBody RoleDTO roleDTO) {
		MsgReturn<List<RoleVO>> msgReturn = new MsgReturn<List<RoleVO>>();
		Page<Role> role = securityService.queryRole(roleDTO, roleDTO.getPage(), 10000);
		List<RoleVO> RoleVO = role.getContent().stream().map(item -> {
			return tranRoleTORoleVO(item);
		}).collect(Collectors.toList());
		msgReturn.success().setData(RoleVO, MsgPageInfo.loadFromPageable(role));
		return new ResponseEntity<MsgReturn<List<RoleVO>>>(msgReturn, HttpStatus.OK);
	}

	private ApiVO tranApiTOApiVO(Api api) {
		ApiVO apiVO = new ApiVO();
		BeanUtils.copyProperties(api, apiVO);
		return apiVO;
	}

	private ElementVO tranElementTOElementVO(Resources resources) {
		ElementVO elementVO = new ElementVO();
		BeanUtils.copyProperties(resources.getElement(), elementVO);
		if (resources.getApi() != null) {
			elementVO.setApi_id(resources.getApi().getId());
		}
		elementVO.setMenu_id(resources.getMenu().getId());
		return elementVO;
	}

	private MenuVO tranMenuTOMenuVO(Menu menu) {
		MenuVO menuVO = new MenuVO();
		BeanUtils.copyProperties(menu, menuVO);
		return menuVO;
	}

	private ResourcesVO tranResourcesTOResourcesVO(Resources resources) {
		ResourcesVO resourcesVO = new ResourcesVO();
		BeanUtils.copyProperties(resources, resourcesVO);
		if (resources.getApi() != null) {
			resourcesVO.setApi(tranApiTOApiVO(resources.getApi()));
		}
		if (resources.getElement() != null) {
			resourcesVO.setElement(tranElementTOElementVO(resources));
		}
		if (resources.getMenu() != null) {
			resourcesVO.setMenu(tranMenuTOMenuVO(resources.getMenu()));
		}
		return resourcesVO;
	}

	private PowerVO tranPowerTOPowerVO(Power power) {
		PowerVO powerVO = new PowerVO();
		BeanUtils.copyProperties(power, powerVO);
		powerVO.setResources(tranResourcesTOResourcesVO(power.getResources()));
		return powerVO;
	}

	private RoleVO tranRoleTORoleVO(Role role) {
		RoleVO roleVO = new RoleVO();
		BeanUtils.copyProperties(role, roleVO);
		Set<Power> power = role.getPowers();
		if (power != null) {
			Set<PowerVO> PowerVO = power.stream().filter(item -> item != null).map(item -> {
				return tranPowerTOPowerVO(item);
			}).collect(Collectors.toSet());
			roleVO.setPowers(PowerVO);
		}
		return roleVO;
	}

	private UserVO tranUserTOUserVO(User user) {
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(user, userVO);
		Set<Role> role = user.getRoles();
		if (role != null) {
			Set<RoleVO> roleVO = role.stream().filter(item -> item != null).map(item -> {
				return tranRoleTORoleVO(item);
			}).collect(Collectors.toSet());
			userVO.setRoleVOs(roleVO);
		}
		return userVO;
	}

	@ApiOperation(value="新增权限")
	@PostMapping(value = "/security/addPower", consumes = "application/json")
	public ResponseEntity<MsgReturn<PowerVO>> addPower(@RequestBody PowerDTO powerDTO) {
		MsgReturn<PowerVO> msgReturn = new MsgReturn<PowerVO>();
		if (ResourceType.API.getValue().equals(powerDTO.getType())) {
			// 新建api
			if (!checkApi(powerDTO)) {
				msgReturn.fail("group,name,url,operation为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			List<Power> powerByUrl = securityService.queryPowerByUrl(powerDTO.getUrl());
			if (powerByUrl != null&&powerByUrl.size()>0) {
				List<String> groups = powerByUrl.stream().map(Power::getResources).map(Resources::getGroup).collect(Collectors.toList());
				if(groups.contains(powerDTO.getGroup())) {
				msgReturn.fail("该权限URL已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
				}
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
		} else if (ResourceType.MENU.getValue().equals(powerDTO.getType())) {
			// 新建menu
			if (!checkMenu(powerDTO)) {
				msgReturn.fail("group,name,operation,key,level为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByKey = securityService.queryPowerByMenuKey(powerDTO.getKey());
			if (powerByKey != null) {
				msgReturn.fail("权限名["+powerByKey.getResources().getName()+"]已经使用了唯一键["+powerDTO.getKey()+"]");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			// 获取上一级菜单名称
			if (power.getResources().getMenu().getParentId().longValue() == 0) {
				powerVO.setParent_name("主目录");
			} else {
				securityService.addNewMenuIntoParent(power);
				Resources resources = securityService.queryResourcesByMenuId(power.getResources().getMenu().getParentId());
				powerVO.setParent_name(resources.getName());
			}
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
		} else if (ResourceType.ELEMENT.getValue().equals(powerDTO.getType())) {
			// 新建element
			if (!checkElement(powerDTO)) {
				msgReturn.fail("name,operation,key,menu_id为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByKey = securityService.queryPowerByEleKey(powerDTO.getKey());
			if (powerByKey != null) {
				msgReturn.fail("权限名["+powerByKey.getResources().getName()+"]已经使用了唯一键["+powerDTO.getKey()+"]");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);

		}
		msgReturn.fail("类型错误");
		return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
	}
	
	private boolean checkElement(PowerDTO powerDTO) {
		if (StringUtils.isEmpty(powerDTO.getGroup()) || StringUtils.isEmpty(powerDTO.getName())
				|| StringUtils.isEmpty(powerDTO.getOperation()) || StringUtils.isEmpty(powerDTO.getKey())
				|| StringUtils.isEmpty(powerDTO.getMenuId())) {
			return false;
		}
		return true;
	}

	private boolean checkMenu(PowerDTO powerDTO) {
		if (StringUtils.isEmpty(powerDTO.getGroup()) || StringUtils.isEmpty(powerDTO.getName())
				|| StringUtils.isEmpty(powerDTO.getOperation()) || StringUtils.isEmpty(powerDTO.getKey())
				|| StringUtils.isEmpty(powerDTO.getLevel())) {
			return false;
		}
		return true;
	}

	private boolean checkApi(PowerDTO powerDTO) {
		if (StringUtils.isEmpty(powerDTO.getGroup()) || StringUtils.isEmpty(powerDTO.getName())
				|| StringUtils.isEmpty(powerDTO.getUrl()) || StringUtils.isEmpty(powerDTO.getOperation())) {
			return false;
		}
		return true;
	}

	@ApiOperation(value="新增角色")
	@PostMapping(value = "/security/addRole", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<RoleVO>>> addRole(@RequestBody RoleDTO roleDTO) {
		MsgReturn<List<RoleVO>> msgReturn = new MsgReturn<List<RoleVO>>();
		if (StringUtils.isEmpty(roleDTO.getName())) {
			msgReturn.fail("角色名不能为空！");
			return new ResponseEntity<MsgReturn<List<RoleVO>>>(msgReturn, HttpStatus.OK);
		}
		if (StringUtils.isEmpty(roleDTO.getDesc())) {
			msgReturn.fail("角色描述不能为空!");
			return new ResponseEntity<MsgReturn<List<RoleVO>>>(msgReturn, HttpStatus.OK);
		}
		// 判断角色名是否重复
		Role roles = securityService.queryRoleByName(roleDTO.getName());
		if (roles != null) {
			msgReturn.fail("该角色名已存在!");
		} else {
			// 角色状态，valid-可用，invalid-不可用,默认为valid
			roleDTO.setStatus(NormalStatus.VALID.getValue());
			securityService.saveRole(roleDTO);
			// 查询所有角色返回
			Page<Role> rolePage = securityService.queryRole(new RoleDTO(), roleDTO.getPage(), 10000);
			List<RoleVO> RoleVO = rolePage.getContent().stream().map(item -> {
				return tranRoleTORoleVO(item);
			}).collect(Collectors.toList());
			msgReturn.success().setData(RoleVO, MsgPageInfo.loadFromPageable(rolePage));
		}
		return new ResponseEntity<MsgReturn<List<RoleVO>>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="修改权限")
	@PostMapping(value = "/security/modPower", consumes = "application/json")
	public ResponseEntity<MsgReturn<PowerVO>> modPower(@RequestBody PowerDTO powerDTO) {
		MsgReturn<PowerVO> msgReturn = new MsgReturn<PowerVO>();
		if (ResourceType.API.getValue().equals(powerDTO.getType())) {
			// 修改api
			if (!checkApi(powerDTO)) {
				msgReturn.fail("group,name,url,operation为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			List<Power> powerByUrl = securityService.queryPowerByUrl(powerDTO.getUrl());
			if (powerByUrl != null&&powerByUrl.size()>0) {
				List<Power> powers = powerByUrl.stream().filter(item->item.getResources().getGroup().equals(powerDTO.getGroup())).filter(item->item.getId().longValue()!=powerDTO.getId().longValue()).collect(Collectors.toList());
				if (powers!=null&&powers.size()>0) {
					msgReturn.fail("该权限URL已存在");
					return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
				}
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null && powerByName.getId().longValue() != powerDTO.getId().longValue()) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
		} else if (ResourceType.MENU.getValue().equals(powerDTO.getType())) {
			// 修改menu
			if (!checkMenu(powerDTO)) {
				msgReturn.fail("group,name,operation,key,level为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByKey = securityService.queryPowerByMenuKey(powerDTO.getKey());
			if (powerByKey != null && powerByKey.getId().longValue() != powerDTO.getId().longValue()) {
				msgReturn.fail("权限名["+powerByKey.getResources().getName()+"]已经使用了唯一键["+powerDTO.getKey()+"]");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null && powerByName.getId().longValue() != powerDTO.getId().longValue()) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			// 获取上一级菜单名称
			if (power.getResources().getMenu().getParentId().longValue() == 0) {
				powerVO.setParent_name("主目录");
			} else {
				Resources resources = securityService.queryResourcesByMenuId(power.getResources().getMenu().getParentId());
				powerVO.setParent_name(resources.getName());
			}
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
		} else if (ResourceType.ELEMENT.getValue().equals(powerDTO.getType())) {
			// 修改element
			if (!checkElement(powerDTO)) {
				msgReturn.fail("name,operation,key,menu_id为必填项");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByKey = securityService.queryPowerByEleKey(powerDTO.getKey());
			if (powerByKey != null && powerByKey.getId().longValue() != powerDTO.getId().longValue()) {
				msgReturn.fail("权限名["+powerByKey.getResources().getName()+"]已经使用了唯一键["+powerDTO.getKey()+"]");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power powerByName = securityService.queryPowerByName(powerDTO.getName());
			if (powerByName != null && powerByName.getId().longValue() != powerDTO.getId().longValue()) {
				msgReturn.fail("该权限名已存在");
				return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
			}
			Power power = securityService.savePower(powerDTO);
			PowerVO powerVO = tranPowerTOPowerVO(power);
			msgReturn.success(powerVO);
			return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);

		}
		msgReturn.fail("类型错误");
		return new ResponseEntity<MsgReturn<PowerVO>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="修改角色")
	@PostMapping(value = "/security/modRole", consumes = "application/json")
	public ResponseEntity<MsgReturn<RoleVO>> modRole(@RequestBody RoleDTO roleDTO) {
		MsgReturn<RoleVO> msgReturn = new MsgReturn<RoleVO>();
		if (StringUtils.isEmpty(roleDTO.getName())) {
			msgReturn.fail("角色名不能为空！");
			return new ResponseEntity<MsgReturn<RoleVO>>(msgReturn, HttpStatus.OK);
		}
		if (StringUtils.isEmpty(roleDTO.getDesc())) {
			msgReturn.fail("角色描述不能为空!");
			return new ResponseEntity<MsgReturn<RoleVO>>(msgReturn, HttpStatus.OK);
		}
		Role roles = securityService.queryRoleByName(roleDTO.getName());
		if (roles != null && roles.getId().longValue() != roleDTO.getId().longValue()) {
			msgReturn.fail("该角色名已存在!");
		} else {
			String roleName = securityService.queryRoleNameById(roleDTO.getId());
			if (roleName == null) {
				msgReturn.fail("角色id" + roleDTO.getId() + "不存在");
			} else if (roleName.equals(SecurityConstants.ADMIN_ROLE_NAME)) {
				msgReturn.fail(SecurityConstants.ADMIN_ROLE_NAME+"不允许修改");
			} else if (roleName.equals(SecurityConstants.BASE_ROLE_NAME) && !roleName.equals(roleDTO.getName())) {
				msgReturn.fail(SecurityConstants.BASE_ROLE_NAME+"不允许修改角色名");
			} else {
				if (StringUtils.isEmpty(roleDTO.getStatus())) {
					roleDTO.setStatus(NormalStatus.VALID.getValue());
				}
				Role role = securityService.saveRole(roleDTO);
				msgReturn.success(tranRoleTORoleVO(role));
			}
		}
		return new ResponseEntity<MsgReturn<RoleVO>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="删除权限")
	@DeleteMapping(value = "/security/delPower", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<Long>>> delPower(@RequestBody List<Long> ids) {
		MsgReturn<List<Long>> msgReturn = new MsgReturn<List<Long>>();
		try {
			msgReturn.success(securityService.delPower(ids));
		} catch (Exception e) {
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="删除角色")
	@DeleteMapping(value = "/security/delRole", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<Long>>> delRole(@RequestBody List<Long> ids) {
		MsgReturn<List<Long>> msgReturn = new MsgReturn<List<Long>>();
		List<String> queryRoleInUser = securityService.queryRoleInUser(ids);
		if (queryRoleInUser.size() > 0) {
			msgReturn.fail("角色:" + queryRoleInUser + "下还有对应的用户，不能删除该角色");
		} else {
			for (Long id : ids) {
				String roleName = securityService.queryRoleNameById(id);
				if (roleName == null) {
					msgReturn.fail("角色id" + id + "不存在");
					return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
				} else if (roleName.equals(SecurityConstants.ADMIN_ROLE_NAME)) {
					msgReturn.fail(SecurityConstants.ADMIN_ROLE_NAME+"不允许删除");
					return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
				} else if (roleName.equals(SecurityConstants.BASE_ROLE_NAME)) {
					msgReturn.fail(SecurityConstants.BASE_ROLE_NAME+"不允许删除");
					return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
				}
			}
			
			List<Long> id = securityService.delRole(ids);
			msgReturn.success(id);
		}
		return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="修改用户")
	@PostMapping(value = "/security/modUser", consumes = "application/json")
	public ResponseEntity<MsgReturn<List<UserVO>>> modUser(@RequestBody UserDTO userDTO) {
		securityService.saveUser(userDTO);
		return this.queryUser(new UserDTO());
	}

	@ApiOperation(value="根据资源名称查询资源信息")
	@PostMapping(value = "/security/queryResources")
	public ResponseEntity<MsgReturn<PowerTreeVO>> queryResourcesByName(@RequestBody PowerDTO powerDTO) {
		MsgReturn<PowerTreeVO> msgReturn = new MsgReturn<PowerTreeVO>();
		Resources resources = new Resources();
		resources.setName(powerDTO.getName());
		resources.setType(powerDTO.getType());
		resources.setStatus(null);
		List<MenuTreeVO> MenuTrees = securityService.queryResourcesByName(resources, powerDTO.getPage(), 10000);
		List<PowerVO> apiVOs = securityService.queryAllPower().stream()
				.filter(a -> a.getResources().getApi() != null && a.getResources().getElement() == null).map(item -> {
					return tranPowerTOPowerVO(item);
				}).collect(Collectors.toList());
		if ((ResourceType.API.getValue()).equals(resources.getType())) {
			apiVOs = apiVOs.stream().filter(a -> a.getResources().getName().contains(resources.getName()))
					.collect(Collectors.toList());
		}
		List<String> group = securityService.queryAllPower().stream().map(Power::getResources).map(Resources::getGroup)
				.distinct().collect(Collectors.toList());
		List<Long> apiId = securityService.queryAllPower().stream()
				.filter(a -> a.getResources().getApi() != null && a.getResources().getElement() == null)
				.map(Power::getResources).map(Resources::getApi).map(Api::getId).collect(Collectors.toList());
		PowerTreeVO powerTreeVO = new PowerTreeVO();
		powerTreeVO.setMenuTreeVOs(MenuTrees);
		powerTreeVO.setApiVOs(apiVOs);
		powerTreeVO.setGroup(group);
		powerTreeVO.setApiId(apiId);
		msgReturn.success().setData(powerTreeVO);
		return new ResponseEntity<MsgReturn<PowerTreeVO>>(msgReturn, HttpStatus.OK);
	}

}
