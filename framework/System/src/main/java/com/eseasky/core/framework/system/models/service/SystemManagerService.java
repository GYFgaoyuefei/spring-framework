package com.eseasky.core.framework.system.models.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.system.exception.GeneralException;
import com.eseasky.core.framework.system.models.entity.DictItem;
import com.eseasky.core.framework.system.models.entity.Dictionary;
import com.eseasky.core.framework.system.models.entity.local.DictionaryConditions;



@Service
public interface SystemManagerService {
	
	/**
	 * 插入字典数据
	 * @param dictionary
	 * @return
	 * @throws GeneralException 
	 */
	Dictionary insertDictionary(Dictionary dictionary) throws GeneralException;
	
	/**
	 * 添加字典项，通过type和group获取字典对象，最终调用addItemToGroup(Dictionary dictionary, List<DictItem> dictItems)
	 * @param type
	 * @param group
	 * @param dictItems
	 * @return
	 * @throws GeneralException 自定义异常
	 */
	Dictionary addItemToGroup(String type, String group, List<DictItem> dictItems) throws GeneralException;
	
	/**
	 * 添加字典项，通过dict_id获取字典对象，最终调用addItemToGroup(Dictionary dictionary, List<DictItem> dictItems)
	 * @param dict_id
	 * @param dictItems
	 * @return
	 * @throws GeneralException
	 */
	Dictionary addItemToGroup(Long dict_id, List<DictItem> dictItems) throws GeneralException;
	
	/**
	 * 添加字典项
	 * @param dictionary
	 * @param dictItems
	 * @return
	 * @throws GeneralException
	 * 	字典不存在时抛出异常
	 *  字典状态无效时抛出异常
	 *  添加的字典项重复时抛出异常
	 *  具体异常信息在com.eseasky.base.common.exception.ExceptionType中配置
	 */
	Dictionary addItemToGroup(Dictionary dictionary, List<DictItem> dictItems) throws GeneralException;
	
	/**
	 * 通过类型和分组查询状态为valid的字典对象
	 * @param type
	 * @param group
	 * @return
	 */
	Dictionary findValidDictByTypeAndGroup(String type, String group);
	
	List<Dictionary> findValidDictByTypeAndGroups(String type, Collection<String> groups);
	
	Map<String, Map<String, String>> findValidDictByTypeAndGroupsForMap(String type, Collection<String> groups);
	
	/**
	 * 通过类型查询状态为valid的字典对象
	 * @param type
	 * @return
	 */
	List<Dictionary> findValidDictByType(String type);
	
	/**
	 * 通过类型和字典状态查询字典对象
	 * @param type
	 * @param status
	 * @return
	 */
	List<Dictionary> findDictByTypeAndStatus(String type, String status);
	
	/**
	 * 通过字典分组，类型以及状态查询字典对象
	 * @param type
	 * @param group
	 * @param status
	 * @return
	 */
	Dictionary findDictByTypeAndGroupAndStatus(String type, String group, String status);

	/**
	 * 通过字典分组，类型查询字典对象
	 * @param type
	 * @param group
	 * @param status
	 * @return
	 */
	Dictionary findDictByTypeAndGroup(String type, String group);

	/**
	 * 更新字典信息
	 * @param updates
	 * 	可更新的字段有：name, desc, status, editable, 以及字典项status, value, name
	 * @return
	 * @throws GeneralException
	 * 	字典不存在时抛出异常
	 */
	Dictionary updateDictionary(Dictionary updates) throws GeneralException;
	
	/**
	 * 删除字典
	 * @param dictionary
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionary(Dictionary dictionary) throws GeneralException;
	
	/**
	 * 删除字典
	 * @param dictionary
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionary(Long dict_id) throws GeneralException;
	
	/**
	 * 删除字典
	 * @param dictionary
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionary(List<Long> dicts) throws GeneralException;
	
	/**
	 * 删除字典
	 * @param dictionary
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionaryByObjects(List<Dictionary> dictionaries) throws GeneralException;
	
	/**
	 * 从字典中删除字典项
	 * @param dictionary
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Dictionary removeItemFromDict(Dictionary dictionary, List<Long> items) throws GeneralException;
	
	/**
	 * 字典高级查询
	 * @param dictionaryConditions
	 * @return
	 */
	Page<Dictionary> queryDictionaries(DictionaryConditions dictionaryConditions);
	
	/**
	 * 查询字典对象
	 * @param dictionaryConditions
	 * @return
	 */
	Dictionary queryOneDict(Long id);
	
	/**
	 * 字典刷新
	 * @return
	 */
	boolean refreshDict();
	
	DictItem getDictItem(String type, String group, String key);
	
	@SuppressWarnings("rawtypes")
	List<Map> getDictItemValue2ListMap(String type, String group, String key);
	
	<T> List<T> getDictItemValue2List(DictItem dictItem, Class<T> clazz);

	List<String> getDictTypes();

	List<String> getDictGroups();
	
	Map<String,Map<String,Object>> getAllDictItemValueByType(String type);
}
