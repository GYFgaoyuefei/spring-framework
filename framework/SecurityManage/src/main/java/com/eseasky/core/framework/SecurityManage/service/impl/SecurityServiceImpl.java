package com.eseasky.core.framework.SecurityManage.service.impl;

import java.util.ArrayList;
//import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eseasky.core.framework.SecurityManage.protocol.dto.PowerDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.RoleDTO;
import com.eseasky.core.framework.SecurityManage.protocol.dto.UserDTO;
import com.eseasky.core.framework.SecurityManage.protocol.vo.MenuTreeVO;
import com.eseasky.core.framework.SecurityManage.service.SecurityService;
import com.eseasky.global.utils.CheckUtils;
import com.eseasky.starter.security.module.model.Api;
import com.eseasky.starter.security.module.model.Element;
import com.eseasky.starter.security.module.model.Menu;
import com.eseasky.starter.security.module.model.Power;
import com.eseasky.starter.security.module.model.Resources;
import com.eseasky.starter.security.module.model.Role;
import com.eseasky.starter.security.module.model.User;
import com.eseasky.starter.security.module.model.enums.NormalStatus;
import com.eseasky.starter.security.module.model.enums.ResourceType;
import com.eseasky.starter.security.module.service.UserService;

@Service
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	UserService userService;

	@Override
	public List<User> queryUser(UserDTO userDTO, int page, int size) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		Role role = new Role();
		role.setId(userDTO.getRoleId());
		role.setName(userDTO.getRoleName());
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		return userService.queryUser(user, page, size).getContent();
	}

	@Override
	public List<User> queryOnlyUser() {
		return userService.queryOnlyUser();
	}
	
	@Override
	public List<Power> queryPower(PowerDTO powerDTO, int page, int size) {
		Power power = new Power();
		Resources resources = new Resources();
		BeanUtils.copyProperties(powerDTO, resources);
		resources.setId(powerDTO.getRes_id());
		BeanUtils.copyProperties(powerDTO, power);
		List<Power> powerList = userService.queryPower(power, page, size).getContent()
				.stream().filter(item->NormalStatus.isValid(item.getStatus())).collect(Collectors.toList());
		
		List<Resources> Resources = userService.queryResources(resources, 0, 10000).getContent();
		for (Resources resourcess : Resources) {
			if (NormalStatus.isValid(resourcess.getStatus())) {
				Power Power = userService.queryPowerbyResources(resourcess);
				if (Power != null && !powerList.contains(Power) && NormalStatus.isValid(Power.getStatus())) {
					powerList.add(Power);
				}
			}
		}
		List<Power> powerUp = new ArrayList<>();
//		List<Power> childMenus = powerList.stream()
//				.filter(item -> item != null 
//					&&(item.getResources().getElement() != null
//							|| (item.getResources().getMenu() != null && !item.getResources().getMenu().getParentId().equals(new Long(0)))))
//				.collect(Collectors.toList());
		for(Power childMenu : powerList) {
			List<Power> ParentMenuPower = userService.findParentMenuPower(childMenu);
			if(!CheckUtils.isEmpty(ParentMenuPower)) {
				powerUp.addAll(ParentMenuPower);
			}
		}
		List<Power> powerDown = new ArrayList<>();
		List<Power> parentMenus = powerList.stream()
				.filter(item -> item != null 
					&& item.getResources().getMenu() != null)
				.collect(Collectors.toList());
		for(Power parentMenu : parentMenus) {
			List<Power> ChildMenuPowers = userService.findChildMenuPower(parentMenu);
			if(!CheckUtils.isEmpty(ChildMenuPowers)) {
				powerDown.addAll(ChildMenuPowers);
			}
		}
		powerList.addAll(powerUp);
		powerList.addAll(powerDown);
		powerList = powerList.stream().distinct().filter(item->NormalStatus.isValid(item.getStatus())).collect(Collectors.toList());
		return powerList;
	}

	@Override
	public List<Resources> queryAllResources() {
		return userService.queryAllResources();
	}

	@Override
	public Page<Role> queryRole(RoleDTO roleDTO, int page, int size) {
		Role role = new Role();
		BeanUtils.copyProperties(roleDTO, role);
		return userService.queryRole(role, page, size);
	}

	@Override
	public Power savePower(PowerDTO powerDTO) {
		Power power = new Power();
		Resources resources = new Resources();
		BeanUtils.copyProperties(powerDTO, power);
		BeanUtils.copyProperties(powerDTO, resources);
		if (StringUtils.isEmpty(powerDTO.getStatus())) {
			power.setStatus(NormalStatus.VALID.getValue());// 设置默认状态
			resources.setStatus(NormalStatus.VALID.getValue());// 设置默认状态
		}
		if (ResourceType.API.getValue().equals(powerDTO.getType())) {
			Api api = new Api();
			BeanUtils.copyProperties(powerDTO, api);
			if (!StringUtils.isEmpty(powerDTO.getId())) { // 修改
				Power powerById = userService.findPowerById(powerDTO.getId());
				api.setId(powerById.getResources().getApi().getId());
				resources.setId(powerById.getResources().getId());
			}
			Api oldApi = userService.findApiByUrl(powerDTO.getUrl());
			if(oldApi!=null) {
				resources.setApi(oldApi);
			} else {	
				Api saveApi = userService.saveApi(api);
				resources.setApi(saveApi);
			}
		} else if (ResourceType.MENU.getValue().equals(powerDTO.getType())) {
			Menu menu = new Menu();
			BeanUtils.copyProperties(powerDTO, menu);
			if (!StringUtils.isEmpty(powerDTO.getId())) { // 修改
				Power powerById = userService.findPowerById(powerDTO.getId());
				menu.setId(powerById.getResources().getMenu().getId());
				resources.setId(powerById.getResources().getId());
			}
			Menu saveMenu = userService.saveMenu(menu);
			resources.setMenu(saveMenu);
		} else {
			Element element = new Element();
			BeanUtils.copyProperties(powerDTO, element);
			if (!StringUtils.isEmpty(powerDTO.getId())) { // 修改
				Power powerById = userService.findPowerById(powerDTO.getId());
				element.setId(powerById.getResources().getElement().getId());
				resources.setId(powerById.getResources().getId());
			}
			Element saveElement = userService.saveElement(element);
			resources.setElement(saveElement);
			Menu menu = userService.findMenuById(powerDTO.getMenuId());
			if (menu != null) {
				resources.setMenu(menu);
			}
			if (!StringUtils.isEmpty(powerDTO.getApiId())) {
				Api api = userService.findApiById(powerDTO.getApiId());
				if (api != null) {
					resources.setApi(api);
				}
			}
		}
		Resources saveResources = userService.saveResources(resources);
		power.setResources(saveResources);
		power.setName(powerDTO.getName() + powerDTO.getOperation());
		Power savePower = userService.savePower(power);
		return savePower;
	}

	@Override
	public Role saveRole(RoleDTO roleDTO) {
		Role role = new Role();
		Set<Power> powers = new HashSet<>();
		BeanUtils.copyProperties(roleDTO, role);
		if (roleDTO.getRes_id()!=null && roleDTO.getRes_id().size() > 0) {
			for (Long id : roleDTO.getRes_id()) {
				Resources resources = userService.findById(id);
				powers.add(userService.queryPowerbyResources(resources));
			}
			role.setPowers(powers);
		}
		Role Role = userService.saveRole(role);
		if (roleDTO.getUserId() != null) {
			User user = userService.findByid(roleDTO.getUserId());
			Set<Role> roles = user.getRoles();
			roles.add(Role);
			user.setRoles(roles);
			userService.saveUser(user);
		}
		return Role;
	}

	@Override
	public List<Long> delPower(List<Long> ids) throws Exception {
		List<Long> allIds = getAllIds(ids);
		if (!CheckUtils.isEmpty(allIds)) {
			return userService.delPower(allIds);
		}
		return null;
	}

	/**
	 * 根据传递的powerid，获取所有子节点id
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	private List<Long> getAllIds(List<Long> ids) throws Exception {
		List<Long> allIds = new ArrayList<>();
		for (Long id : ids) {
			Power power = userService.findPowerById(id);
			if (!CheckUtils.isEmpty(power)) {
				allIds.add(id);
				if (power.getResources() != null) {
					if (ResourceType.API.getValue().equals(power.getResources().getType()) && power.getResources().getApi() != null) {
						String elementNames = "";
						Api api = new Api ();
						api.setId(power.getResources().getApi().getId());
						for(Resources resources : userService.findResourcesByApi(api)) {
							if (resources.getElement() != null && ResourceType.ELEMENT.getValue().equals(resources.getType())) {
								elementNames += resources.getName() + ",";
							}
						}
						if (!CheckUtils.isEmpty(elementNames)) {
							throw new Exception("元素[" + elementNames.substring(0, elementNames.length()-1) 
								+ "]已经使用了api["+power.getResources().getName()+"]");
						}
					} else {
						List<Power> children = userService.findChildMenuPower(power);
						if (!CheckUtils.isEmpty(children)) {
							allIds.addAll(children.stream().map(item -> item.getId()).distinct().collect(Collectors.toList())); 
						}
					}
				}
			}
		}
		//获取ID逆序去重排序，以防先删除父菜单节点后删除子元素节点失败的bug
		return allIds.stream().distinct().sorted( (x,y) -> y.compareTo(x)).collect(Collectors.toList());
	}

	@Override
	public List<Long> delRole(List<Long> ids) {
		return userService.delRole(ids);
	}

	@Override
	public List<String> queryRoleInUser(List<Long> ids) {
		return userService.queryRoleInUser(ids);
	}

	@Override
	public User queryByid(Long id) {
		return userService.findByid(id);
	}

	@Override
	public Role queryRoleById(Long id) {
		return userService.findRoleById(id);
	}

	@Override
	public Role queryRoleByName(String name) {
		return userService.findRoleByName(name);
	}

	@Override
	public Resources queryResourcesByMenuId(Long menuId) {
		Menu menu = userService.findMenuById(menuId);
		if (menu != null) {
			return userService.findResourcesByMenu(menu).stream().filter(a -> a.getElement() == null)
					.collect(Collectors.toList()).get(0);
		}
		return null;
	}

	@Override
	public Power queryPowerByName(String name) {
		Resources resources = userService.findResourcesByName(name);
		if (resources != null) {
			return userService.queryPowerbyResources(resources);
		}
		return null;
	}

	@Override
	public List<Resources> queryResourcesBySearch(Resources resources, int page, int pageSize) {
		List<Resources> resourcess = userService.queryResources(resources, page, pageSize).getContent();
		return resourcess;
	}

	@Override
	public User saveUser(UserDTO userDTO) {
		User user = userService.findByid(userDTO.getId());
		List<Long> role_id = userDTO.getRole_id();
		Set<Role> roles = new HashSet<>();
		for (Long id : role_id) {
			Role role = this.queryRoleById(id);
			roles.add(role);
		}
		user.setRoles(roles);
		return userService.saveUser(user);
	}

	@Override
	public List<MenuTreeVO> queryResourcesByName(Resources resources, int page, int size) {
		List<MenuTreeVO> listMenuTree = new ArrayList<>();
		List<MenuTreeVO> menuTrees = new ArrayList<>();
		if (resources.getName() == null) {
			List<MenuTreeVO> menuResources = new ArrayList<>();
			resources.setType(ResourceType.MENU.getValue());
			menuResources = userService.queryResources(resources, page, size).getContent().stream()
					.filter(a -> (NormalStatus.isValid(a.getStatus()) && userService.queryPowerbyResources(a)!=null))
					.map(item -> {
				return tranResourcesToMenuTree(item);
			}).collect(Collectors.toList());
			menuTrees.addAll(menuResources);
			resources.setType(ResourceType.ELEMENT.getValue());
			List<MenuTreeVO> elementResources = userService.queryResources(resources, page, size).getContent().stream()
					.filter(a -> (NormalStatus.isValid(a.getStatus()) && userService.queryPowerbyResources(a)!=null))
					.map(item -> {
						MenuTreeVO elementResource = tranResourcesToMenuTree(item);
						elementResource.setParentId(elementResource.getMenuId());
						elementResource.setMenuId((long) -1);
						return elementResource;
					}).collect(Collectors.toList());
			menuTrees.addAll(elementResources);
		} else {
			List<MenuTreeVO> menuTreeVO = userService.queryResources(resources, page, size).getContent().stream()
					.filter(a -> (NormalStatus.isValid(a.getStatus()) && userService.queryPowerbyResources(a)!=null))
					.map(item -> {
						return tranResourcesToMenuTree(item);
					}).filter(a -> !a.getType().equals(ResourceType.API.getValue())).collect(Collectors.toList());
			menuTreeVO.stream().filter(a -> a.getType().equals(ResourceType.ELEMENT.getValue())).map(item -> {
				item.setParentId(item.getMenuId());
				item.setMenuId((long) -1);
				return item;
			}).collect(Collectors.toList());
			for (MenuTreeVO MenuTreeVO : menuTreeVO) {
				menuTrees.addAll(tranParentMenuTree(MenuTreeVO));
				menuTrees.add(MenuTreeVO);
			}
		}
		List<MenuTreeVO> menuRootResources = menuTrees.stream().filter(item -> item.getParentId() == 0)
				.collect(Collectors.toList());
		for (MenuTreeVO menuTreeVO : menuRootResources) {
			MenuTreeVO listMenu = tranMenuTree(menuTrees, menuTreeVO);
			listMenuTree.add(listMenu);
		}
		listMenuTree = listMenuTree.stream().distinct().collect(Collectors.toList());
		return listMenuTree;
	}

	private MenuTreeVO tranMenuTree(List<MenuTreeVO> menuTrees, MenuTreeVO menuTreeVO) {
		List<MenuTreeVO> list = menuTrees.stream()
				.filter(item -> item.getParentId().longValue() == menuTreeVO.getMenuId().longValue()).map(a -> {
					a.setParentName(menuTreeVO.getName());
					return a;
				}).collect(Collectors.toList());
		if (list != null && list.size() > 0) {
			menuTreeVO.setChildren(list);
			for (MenuTreeVO childResources : list) {
				tranMenuTree(menuTrees, childResources);
			}
		}
		return menuTreeVO;
	}

	private List<MenuTreeVO> tranParentMenuTree(MenuTreeVO menuTrees) {
		List<MenuTreeVO> MenuTreeVO = new ArrayList<>();
		if (menuTrees.getParentId() != 0) {
			Resources resources = userService.findById(menuTrees.getParentId());
			if (resources != null) {
				Power power = userService.queryPowerbyResources(resources);
				if (power!=null && resources.getMenu()!=null) {
					MenuTreeVO menuTreeVO = tranResourcesToMenuTree(resources);
					MenuTreeVO.add(menuTreeVO);
					tranParentMenuTree(menuTreeVO);
				}
			}
		}
		return MenuTreeVO;
	}

	private MenuTreeVO tranResourcesToMenuTree(Resources Resources) {
		MenuTreeVO MenuTreeVO = new MenuTreeVO();
		BeanUtils.copyProperties(Resources, MenuTreeVO);
		if (Resources.getMenu() != null) {
			MenuTreeVO.setMenuId(Resources.getMenu().getId());
			MenuTreeVO.setMenuKey(Resources.getMenu().getKey());
			MenuTreeVO.setParentId(Resources.getMenu().getParentId());
			MenuTreeVO.setMenuLink(Resources.getMenu().getLink());
			MenuTreeVO.setTitle(Resources.getName());
			MenuTreeVO.setKey(Resources.getId().toString());
			MenuTreeVO.setLevel(Resources.getMenu().getLevel());
			MenuTreeVO.setOrder(Resources.getMenu().getOrder());
			MenuTreeVO.setIcon(Resources.getMenu().getIcon());
		}
		if (Resources.getElement() != null) {
			MenuTreeVO.setElementId(Resources.getElement().getId());
			MenuTreeVO.setElementKey(Resources.getElement().getKey());
			MenuTreeVO.setElementLink(Resources.getElement().getLink());
			MenuTreeVO.setStyle(Resources.getElement().getStyle());
			if (Resources.getApi() != null) {
				MenuTreeVO.setApiId(Resources.getApi().getId());
			}
		}
		return MenuTreeVO;

	}

	@Override
	public List<Power> queryPowerByUrl(String url) {
		List<Power> powers = new ArrayList<>();
		Api api = userService.findApiByUrl(url);
		if (api != null) {
			List<Resources> resources = userService.findResourcesByApi(api).stream().filter(a -> a.getElement() == null)
					.collect(Collectors.toList());
			if (resources != null) {
				for(Resources resource : resources) {
					powers.add(userService.queryPowerbyResources(resource));
				}
				return powers;
			}
		}
		return null;
	}

	@Override
	public List<Power> queryApiResources() {
		List<Power> powers = userService.queryAllResources().stream()
				.filter(a -> a.getApi() != null && a.getElement() == null).map(item -> {
					Power power = userService.queryPowerbyResources(item);
					return power;
				}).filter(a -> a!=null).collect(Collectors.toList());
		return powers;
	}

	@Override
	public List<Api> queryAllApi() {
		return userService.queryAllApi();
	}

	@Override
	public Power queryPowerByMenuKey(String key) {
		Menu menu = userService.findMenuByKey(key);
		if (menu != null) {
			for(Resources resources : userService.findResourcesByMenu(menu).stream().filter(a -> a.getElement() == null)
					.collect(Collectors.toList())) {
				return userService.queryPowerbyResources(resources);
			}
			//如果没有找到对应的权限资源，即为脏数据，尝试清掉
			userService.deleteMenu(menu);
		}
		return null;
	}

	@Override
	public Power queryPowerByEleKey(String key) {
		Element element = userService.findElementByKey(key);
		if (element != null) {
			for(Resources resources : userService.findResourcesByElement(element)) {
				return userService.queryPowerbyResources(resources);
			}
			//如果没有找到对应的权限资源，即为脏数据，尝试清掉
			userService.deleteElement(element);
		}
		return null;
	}

	@Override
	public List<Power> queryAllPower() {
		return userService.queryAllPower();
	}

	@Override
	public List<Power> queryPowerByMenuParentId(Long id) {
		List<Power> powers = new ArrayList<>();
		List<Menu> menus = userService.findMenuByParentId(id);
		if (menus!=null && menus.size()>0) {
			powers = menus.stream().map(item -> {
				Resources resources = userService.findResourcesByMenu(item).stream().filter(a -> a.getElement() == null)
						.collect(Collectors.toList()).get(0);
				return resources;
			}).map(item->{
				return userService.queryPowerbyResources(item);
			}).filter(a->a!=null).collect(Collectors.toList());
		}
		return powers;
	}

	@Override
	public void addNewMenuIntoParent(Power power) {
		//入参校验
		if (power.getResources().getMenu() == null) {//|| power.getResources().getMenu().getParentId().longValue() == 0
			return;
		}
		//父节点拥有其他子节点则不进行该操作
		List<Menu> menus = userService.findMenuByParentId(power.getResources().getMenu().getParentId());
		for (Menu menu : menus) {
			if (menu.getId().longValue() != power.getResources().getMenu().getId().longValue()) {
				return;
			}
		}
		//获取拥有父节点菜单的角色id
		Resources parentResources = queryResourcesByMenuId(power.getResources().getMenu().getParentId());
		if (parentResources != null) {
			Power parentPower = userService.queryPowerbyResources(parentResources);
			if (parentPower != null) {
				//将该菜单权限赋给  获取拥有父节点菜单的角色
				for (Long roleId : userService.findRoleIdsByPowId(parentPower.getId())) {
					userService.addRolePowerRelation(roleId,power.getId());
				}
			}
		}

	}

	@Override
	public String queryRoleNameById(Long id) {
		return userService.findRoleNameById(id);
	}


}
