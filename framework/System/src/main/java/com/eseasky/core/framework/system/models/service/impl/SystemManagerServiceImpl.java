package com.eseasky.core.framework.system.models.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eseasky.core.framework.system.exception.ExceptionType;
import com.eseasky.core.framework.system.exception.GeneralException;
import com.eseasky.core.framework.system.models.entity.DictItem;
import com.eseasky.core.framework.system.models.entity.Dictionary;
import com.eseasky.core.framework.system.models.entity.enums.DictionaryStatus;
import com.eseasky.core.framework.system.models.entity.local.DictionaryConditions;
import com.eseasky.core.framework.system.models.repository.DictItemRepository;
import com.eseasky.core.framework.system.models.repository.DictionaryRepository;
import com.eseasky.core.framework.system.models.service.SystemManagerService;
import com.eseasky.global.utils.CheckUtils;

@Service
public class SystemManagerServiceImpl implements SystemManagerService {
	@Autowired
	DictionaryRepository dictionaryRepository;
	
	@Autowired
	DictItemRepository dictItemRepository;
	
	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary insertDictionary(Dictionary dictionary) throws GeneralException {
		if(dictionary.getDesc().length()>255||dictionary.getGroup().length()>255||dictionary.getName().length()>255||dictionary.getType().length()>255) {
			throw new GeneralException(ExceptionType.SYSTEFieldTooLong);
		}
		Dictionary dictionaryRet = dictionaryRepository.findByTypeAndGroup(dictionary.getType(), dictionary.getGroup());
		if ( (dictionary.getId() == null && dictionaryRet != null) 
				|| (dictionary.getId() != null && !dictionary.getId().equals(dictionaryRet.getId()))) {
			throw new GeneralException(ExceptionType.SYSTEEXISTSDICT);
		}

		if (!CheckUtils.isEmpty(dictionary.getDictItems())) {
			Set<String> duplicateKeys = checkDictItemsDuplicate(dictionary.getDictItems());
			if (!CheckUtils.isEmpty(duplicateKeys)) {
				throw new GeneralException(ExceptionType.SYSTEMHASSAMEDICTITEM, "重复枚举值为："+duplicateKeys);
			}
			for(DictItem dItem:dictionary.getDictItems()) {
				if(dItem.getKey().length()>255||dItem.getName().length()>255||dItem.getValue().length()>4096) {
					throw new GeneralException(ExceptionType.SYSTEFieldTooLong);
				}
			}
		}

		dictionaryRet = dictionaryRepository.save(dictionary);
		if (!CheckUtils.isEmpty(dictionary.getDictItems())) {
//			for (int i = 0; i < dictionary.getDictItems().size(); i++) {
//				dictionary.getDictItems().get(i).setDictionary(dictionaryRet);
//			}
			Dictionary temp = dictionaryRet;
			List<DictItem> dictItems = dictionary.getDictItems().stream()
					.map(item -> {item.setDictionary(temp);return item;})
					.collect(Collectors.toList());
			dictionaryRet.setDictItems(dictItemRepository.saveAll(dictItems));
		}
		return dictionaryRet;
	}

	private Set<String> checkDictItemsDuplicate(List<DictItem> dictItems) {
		Set<String> existKeys = new HashSet<String> ();
		Set<String> duplicateKeys = new HashSet<String> ();
		for(DictItem dictItem:dictItems) {
			if (existKeys.contains(dictItem.getKey())) {
				duplicateKeys.add(dictItem.getKey());
			} else {
				existKeys.add(dictItem.getKey());
			}
		}
		
		return duplicateKeys;
	}

	
	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary addItemToGroup(String type, String group, List<DictItem> dictItems) throws GeneralException {
		Dictionary dictionary = dictionaryRepository.findByTypeAndGroup(type, group);
		return addItemToGroup(dictionary, dictItems);
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary addItemToGroup(Long dict_id, List<DictItem> dictItems) throws GeneralException {
		Dictionary dictionary = dictionaryRepository.getOne(dict_id);
		return addItemToGroup(dictionary, dictItems);
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary addItemToGroup(Dictionary dictionary, List<DictItem> dictItems) throws GeneralException {
		if (dictionary == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
		if (!dictionary.getStatus().equals("valid")) {
			throw new GeneralException(ExceptionType.SYSTEMDICTISINVALID);
		}
		if (dictItems == null || dictItems.size() == 0) {
			return dictionary;
		}
		List<DictItem> dItems = dictionary.getDictItems();
		if (dItems == null) {
			dItems = new ArrayList<DictItem>();
		}
		for (DictItem dictItem: dictItems) {
			for (DictItem dItem: dItems) {
				if(dItem.getKey().length()>255||dItem.getName().length()>255||dItem.getValue().length()>4096) {
					throw new GeneralException(ExceptionType.SYSTEFieldTooLong);
				}
				if (dictItem.getKey().equals(dItem.getKey())) {
					throw new GeneralException(ExceptionType.SYSTEMHASSAMEDICTITEM);
				}
			}
			dictItem.setDictionary(dictionary);
			dItems.add(dictItem);
		}
		dictionary.setDictItems(dItems);
		return insertDictionary(dictionary);
	}
 
	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findValidDictByTypeAndGroup'+#type+#group",unless="#result == null")
	public Dictionary findValidDictByTypeAndGroup(String type, String group) {
		return findDictByTypeAndGroupAndStatus(type, group, DictionaryStatus.VALID.getValue());
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findValidDictByType'+#type",unless="#result == null")
	public List<Dictionary> findValidDictByType(String type) {
		return findDictByTypeAndStatus(type, DictionaryStatus.VALID.getValue());
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findDictByTypeAndStatus'+#type+#status",unless="#result == null")
	public List<Dictionary> findDictByTypeAndStatus(String type, String status) {
		List<Dictionary> dictionarys =dictionaryRepository.findByTypeAndStatus(type, status);
		for (int i = 0; i < dictionarys.size(); i++) {
			if (dictionarys.get(i).getDictItems() != null && dictionarys.get(i).getDictItems().size() > 0) {
				List<DictItem> dictItems = dictionarys.get(i).getDictItems().stream()
						.filter(item -> status.equals(item.getStatus())).collect(Collectors.toList());
				dictionarys.get(i).setDictItems(dictItems);
			}
		}
		return dictionarys;
	}

	
	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findDictByTypeAndGroupAndStatus'+#type+#group+#status",unless="#result == null")
	public Dictionary findDictByTypeAndGroupAndStatus(String type, String group, String status) {
		Dictionary dictionary =dictionaryRepository.findByTypeAndGroupAndStatus(type, group, status);
		if (dictionary != null && dictionary.getDictItems() != null && dictionary.getDictItems().size() > 0) {
			List<DictItem> dictItems = dictionary.getDictItems().stream()
					.filter(item -> status.equals(item.getStatus())).collect(Collectors.toList());
			dictionary.setDictItems(dictItems);
		}
		return dictionary;
	}

	public Dictionary findDictByTypeAndGroup(String type, String group) {
		return dictionaryRepository.findByTypeAndGroup(type, group);
	}
	
	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary updateDictionary(Dictionary updates) throws GeneralException {
		if (updates.getId() == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
		Dictionary oldDictionary  = dictionaryRepository.getOne(updates.getId());
		if (oldDictionary == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
		oldDictionary.setDesc(updates.getDesc() != null ? updates.getDesc() : oldDictionary.getDesc());
		oldDictionary.setName(updates.getName() != null ? updates.getName() : oldDictionary.getName());
		oldDictionary.setStatus(updates.getStatus() != null ? updates.getStatus() : oldDictionary.getStatus());
		
		if(oldDictionary.getDesc().length()>255||oldDictionary.getGroup().length()>255||oldDictionary.getType().length()>255||oldDictionary.getName().length()>255) {
			throw new GeneralException(ExceptionType.SYSTEFieldTooLong);
		}
		List<DictItem> dictItems = getUpdateDictItem(oldDictionary.getDictItems(), updates.getDictItems());
		if (updates.getStatus() != null && updates.getStatus().equals(oldDictionary.getStatus())) {
			for (int i = 0; i < dictItems.size(); i++) {
				if(dictItems.get(i).getKey().length()>255||dictItems.get(i).getName().length()>255||dictItems.get(i).getValue().length()>4096) {
					throw new GeneralException(ExceptionType.SYSTEFieldTooLong);
				}
				dictItems.get(i).setStatus(updates.getStatus());
			}
		}
		if (dictItems != null && dictItems.size() > 0) {
			oldDictionary.setDictItems(dictItems);
		}
		return insertDictionary(oldDictionary);
	}
	
	/**
	 * 合并字典项,将信息字典项合并到旧的字典项，合并规则：
	 * 	1 若已存在，则替换name，status，value中出现的值
	 * 	2 若不存在则新增
	 * @param old
	 * @param newItems
	 * @return
	 */
	private List<DictItem> getUpdateDictItem(List<DictItem> old, List<DictItem> newItems){
		if (newItems == null || newItems.size() == 0) {
			return old;
		}
		if (old == null || old.size() == 0) {
			return newItems;
		}
		List<DictItem> newDictItems = new ArrayList<DictItem>();
		newDictItems.addAll(newItems);
		List<DictItem> oldDictItems = new ArrayList<DictItem>();
		oldDictItems.addAll(old);
		List<DictItem> items = new ArrayList<DictItem>();
		for (int i = 0; i < oldDictItems.size(); i ++) {
			for (int m = 0; m < newDictItems.size(); m ++) {
				if (oldDictItems.get(i).getKey().equals(newDictItems.get(m).getKey())) {
					oldDictItems.set(i, getRepeatDict(oldDictItems.get(i), newDictItems.get(m)));
					newDictItems.remove(m);
					break;
				}
			}
		}
		items.addAll(oldDictItems);
		if (newDictItems != null && newDictItems.size() > 0) {
			items.addAll(newDictItems);
		}
		
		return items;
	}
	
	/**
	 * 合并重复的字典项
	 * @param old
	 * @param repeat
	 * @return
	 */
	private DictItem getRepeatDict(DictItem old, DictItem repeat) {
		DictItem newItem = old;
		newItem.setName(repeat.getName() != null ? repeat.getName() : old.getName());
		newItem.setStatus(repeat.getStatus() != null ? repeat.getStatus() : old.getStatus());
		newItem.setValue(repeat.getValue() != null ? repeat.getValue() : old.getValue());
		return newItem;
	}
	
	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Boolean deleteDictionary(Dictionary dictionary) throws GeneralException {
		if (dictionary == null || dictionary.getId() == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
		try {
			if (dictionary.getDictItems() != null) {
				dictItemRepository.deleteAll(dictionary.getDictItems());
			}
			dictionaryRepository.deleteById(dictionary.getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(ExceptionType.SYSTEMDICTDELETEERROR);
		}
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Boolean deleteDictionary(Long dict_id) throws GeneralException {
		return deleteDictionary(dictionaryRepository.getOne(dict_id));
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Dictionary removeItemFromDict(Dictionary dictionary, List<Long> items) throws GeneralException {
		try {
			List<DictItem> dictItems = new ArrayList<DictItem>();
			dictItems.addAll(dictionary.getDictItems());
			List<DictItem> removes = new ArrayList<DictItem>();
			for (int i = 0; i < dictItems.size(); i++) {
				if (items.contains(dictItems.get(i).getId())) {
					dictItemRepository.delete(dictItems.get(i));
					removes.add(dictItems.get(i));
				}
			}
			dictItems.removeAll(removes);
			if (dictItems.size() > 0) {
				dictionary.setDictItems(new ArrayList<DictItem>(dictItems));
			} else {
				dictionary.setDictItems(null);
			}
			return insertDictionary(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(ExceptionType.SYSTEMDICTDREMOVEITEMERROR);
		}
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'queryDictionaries'+#dictionaryConditions",unless="#result == null")
	public Page<Dictionary> queryDictionaries(DictionaryConditions dictionaryConditions) {
		int page = dictionaryConditions != null ? dictionaryConditions.getPage() : 0;
		int pageSize = dictionaryConditions != null ? dictionaryConditions.getPageSize() : 10;
		Pageable pageable = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "id"));
		if (dictionaryConditions != null) {
			Specification<Dictionary> querySpecifi = new Specification<Dictionary>() {
				private static final long serialVersionUID = 1L;
				@Override
				public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> criteriaQuery,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
					if (dictionaryConditions.getIds() != null && dictionaryConditions.getIds().size()>0) {
						In<Long> idIn = criteriaBuilder.in(root.get("id"));
						List<Long> idsList = dictionaryConditions.getIds();
						for (int i = 0; i < idsList.size(); i++) {
							idIn.value(idsList.get(i));
						}
						predicates.add(idIn);
					}
					if (null != dictionaryConditions.getType() && !"".equals(dictionaryConditions.getType().trim())) {
						predicates.add(criteriaBuilder.like(root.get("type"), "%" + dictionaryConditions.getType().trim() + "%"));
					}
					if (null != dictionaryConditions.getName() && !"".equals(dictionaryConditions.getName().trim())) {
						predicates.add(criteriaBuilder.like(root.get("name"), "%" + dictionaryConditions.getName().trim() + "%"));
					}
					if (null != dictionaryConditions.getGroup() && !"".equals(dictionaryConditions.getGroup().trim())) {
						predicates.add(criteriaBuilder.like(root.get("group"), "%" + dictionaryConditions.getGroup().trim() + "%"));
					}
					if (null != dictionaryConditions.getStatus()) {
						predicates
								.add(criteriaBuilder.equal(root.get("status"), dictionaryConditions.getStatus()));
					}
					if (dictionaryConditions.getItemKey() != null || dictionaryConditions.getItemName() != null || 
							dictionaryConditions.getItemStatus() != null) {
						Join<DictItem, Dictionary> dictItem = root.join("dictItems", JoinType.LEFT);
						if (dictionaryConditions.getItemKey() != null && !"".equals(dictionaryConditions.getItemKey().trim())) {
							predicates.add(criteriaBuilder.like(dictItem.get("key"), "%" + dictionaryConditions.getItemKey().trim() + "%"));
						}
						if (dictionaryConditions.getItemName() != null && !"".equals(dictionaryConditions.getItemName().trim())) {
							predicates.add(criteriaBuilder.like(dictItem.get("name"), "%" + dictionaryConditions.getItemName().trim() + "%"));
						}
						if (dictionaryConditions.getItemStatus() != null) {
							predicates.add(criteriaBuilder.equal(dictItem.get("status"), dictionaryConditions.getItemStatus()));
						}
						criteriaQuery.groupBy(root.get("id"));
					}
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			return dictionaryRepository.findAll(querySpecifi, pageable);
		} else {
			return dictionaryRepository.findAll(pageable);
		}
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Boolean deleteDictionary(List<Long> dicts) throws GeneralException {
		try {
//			List<Dictionary> dictionaries = dictionaryRepository.findByIdIn(dicts);
//			return this.deleteDictionaryByObjects(dictionaries);
			for (Long id : dicts) {
				deleteDictionary(queryOneDict(id));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(ExceptionType.SYSTEMDICTDELETEERROR);
		}
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public Boolean deleteDictionaryByObjects(List<Dictionary> dictionaries) throws GeneralException {
		try {
			//dictionaryRepository.deleteInBatch(dictionaries);
			for (Dictionary dictionary : dictionaries) {
				deleteDictionary(dictionary);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(ExceptionType.SYSTEMDICTDELETEERROR);
		}
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'queryOneDict'+#id")
	public Dictionary queryOneDict(Long id) {
		return dictionaryRepository.getOne(id);
	}

	@Override
	@CacheEvict(value = "sys_dictionary", allEntries = true)
	public boolean refreshDict() {
		return false;
	}

	@Override
//	@Cacheable(value= {"sys_dictionary", "sys_dict_item"})
	public DictItem getDictItem(String type, String group, String key) {
		Dictionary dictionary = this.findValidDictByTypeAndGroup(type, group);
		if (dictionary != null) {
			List<DictItem> dictItems = dictionary.getDictItems();
			if (dictItems != null) {
				Optional<DictItem> dictItem = dictItems.stream().filter(item -> item.getKey().equals(key)).findFirst();
				if (dictItem.isPresent()) return dictItem.get();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
//	@Cacheable(value= {"sys_dictionary", "sys_dict_item"})
	public List<Map> getDictItemValue2ListMap(String type, String group, String key) {
		DictItem dictItem = getDictItem(type, group, key);
		if (dictItem != null) {
			String value = dictItem.getValue();
			if (value != null) {
				List<Map> map = getDictItemValue2List(dictItem, Map.class);
				if (map != null) return map;
			}
		}
		return null;
	}

	@Override
	public <T> List<T> getDictItemValue2List(DictItem dictItem, Class<T> clazz) {
		if (dictItem != null && dictItem.getValue() != null) return transJSONStr2List(dictItem.getValue(), clazz);
		return null;
	}
	
	private <T> List<T> transJSONStr2List(String str, Class<T> clazz){
		str = str.trim();
		List<T> result = null;
		try {
			if (str.startsWith("[")) {
				result = JSON.parseArray(str, clazz);
			} else if (str.startsWith("{")) {
				T obj = JSON.parseObject(str, clazz);
				result = new ArrayList<T>();
				result.add(obj);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
		return result;
	}

	@Override
	public List<String> getDictTypes() {
		return dictionaryRepository.getDictTypes();
	}

	@Override
	public List<String> getDictGroups() {
		return dictionaryRepository.getDictGroups();
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'getAllDictItemValueByType'+#type",unless="#result == null")
	public Map<String, Map<String, Object>> getAllDictItemValueByType(String type) {
		Map<String, Map<String, Object>> map = new HashMap<>();
		List<Dictionary> listDictionary = dictionaryRepository.findByTypeAndStatus(type, DictionaryStatus.VALID.getValue());
		if (!CheckUtils.isEmpty(listDictionary)) {
			List<DictItem> items = null;
			for (Dictionary item : listDictionary) {
				items = item.getDictItems();
				if (CheckUtils.isEmpty(items)) {
					continue;
				}
				Map<String, Object> dictItemMap = new HashMap<>();
				for (DictItem dictItem : items) {
					dictItemMap.put(dictItem.getKey().trim(), dictItem.getValue());
				}
				map.put(item.getGroup(), dictItemMap);
			}
			return map;
		}
		return null;
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findValidDictByTypeAndGroups'+#type+#groups",unless="#result == null")
	public List<Dictionary> findValidDictByTypeAndGroups(String type, Collection<String> groups) {
		// TODO Auto-generated method stub
		if (groups == null) {
			return null;
		}
		List<String> groupList = new ArrayList<>();
		for(String group: groups) {
			groupList.add(group);
		}
		return dictionaryRepository.findByTypeAndGroupInAndStatus(type, groupList,DictionaryStatus.VALID.getValue());
	}

	@Override
	@Cacheable(value = { "sys_dictionary" }, key = "'findValidDictByTypeAndGroupsForMap'+#type+#groups",unless="#result == null")
	public Map<String, Map<String, String>> findValidDictByTypeAndGroupsForMap(String type, Collection<String> groups) {
		// TODO Auto-generated method stub
		List<Dictionary> listDictionary = findValidDictByTypeAndGroups(type, groups);
		if (!CheckUtils.isEmpty(listDictionary)) {
			Map<String, Map<String, String>> map = new HashMap<>();
			List<DictItem> items = null;
			for (Dictionary item : listDictionary) {
				items = item.getDictItems();
				if (CheckUtils.isEmpty(items)) {
					continue;
				}
				Map<String, String> dictItemMap = new HashMap<>();
				for (DictItem dictItem : items) {
					dictItemMap.put(dictItem.getKey().trim(), dictItem.getValue());
				}
				map.put(item.getGroup(), dictItemMap);
			}
			return map;
		}
		return null;
	}
}
