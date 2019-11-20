package com.eseasky.core.framework.SecurityManage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.SecurityManage.protocol.dto.PowerDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.RoleDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.UserDTO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.MenuTreeVO;
import com.eseasky.starter.security.module.model.Api;
import com.eseasky.starter.security.module.model.Power;
import com.eseasky.starter.security.module.model.Resources;
import com.eseasky.starter.security.module.model.Role;
import com.eseasky.starter.security.module.model.User;

@Service
public interface SecurityService {
	User queryByid(Long id);
	
	List<User> queryUser(UserDTO userDTO, int page, int size);
	
	List<Power> queryPower(PowerDTO powerDTO, int page, int size);
	
//	List<MenuTreeVO> queryResources(Resources resources, int page, int size);
	
	List<Resources> queryAllResources();
	
	List<Power> queryAllPower();
	
	Page<Role> queryRole(RoleDTO roleDTO, int page, int size);
	
	Role queryRoleById(Long id);
	
//	Page<Resources> queryResources(ResourcesDTO resourcesDTO, int page, int size);
	
	Power savePower(PowerDTO powerDTO);
	
	Role saveRole(RoleDTO roleDTO);
	
//	Resources saveResources(ResourcesDTO resourcesDTO);
	
	List<Long> delPower(List<Long> ids) throws Exception;
	
	List<Long> delRole(List<Long> ids);
	
	List<String> queryRoleInUser(List<Long> ids);

	Role queryRoleByName(String name);

	Resources queryResourcesByMenuId(Long menuId);

	Power queryPowerByName(String name);

	List<Resources> queryResourcesBySearch(Resources resources, int page, int pageSize);

	User saveUser(UserDTO userDTO);

	List<MenuTreeVO> queryResourcesByName(Resources resources, int page, int size);

	List<Power> queryPowerByUrl(String url);

	Power queryPowerByMenuKey(String key);

	Power queryPowerByEleKey(String key);

	List<Power> queryApiResources();

	List<Api> queryAllApi();

	List<Power> queryPowerByMenuParentId(Long id);

	void addNewMenuIntoParent(Power power);

	List<User> queryOnlyUser();

	String queryRoleNameById(Long id);

//	List<Long> delResources(List<Long> ids);

}
