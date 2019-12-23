package com.eseasky.core.framework.system.service;

import java.util.List;
import java.util.Map;

import com.eseasky.core.framework.system.protocol.vo.DictItemVo;
import com.eseasky.protocol.system.entity.VO.DictItemVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.eseasky.core.framework.system.protocol.dto.DictItemDto;
import com.eseasky.core.framework.system.protocol.dto.DictiCondiDto;
import com.eseasky.core.framework.system.protocol.dto.DictionaryDto;
import com.eseasky.core.framework.system.protocol.vo.DictionaryVo;
import com.eseasky.starter.dictionary.exception.GeneralException;
import com.eseasky.starter.dictionary.module.model.DictItem;
import com.eseasky.starter.dictionary.module.model.Dictionary;


@Service
public interface SystemDictService {
	/**
	 * 插入字典数据
	 * @param dictionary
	 * @return
	 * @throws GeneralException 
	 */
	DictionaryVo insertDictionary(DictionaryDto dictionary) throws GeneralException;
	
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
	 * @return
	 * @throws GeneralException
	 */
	DictionaryVo addItemToGroup(Long dict_id, List<DictItemDto> dictItemDTOs) throws GeneralException;
	
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
	
	/**
	 * 通过类型查询状态为valid的字典对象
	 * @param type
	 * @return
	 */
	List<DictionaryVo> findValidDictByType(String type);
	
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
	 * 更新字典信息
	 * @param updates
	 * 	可更新的字段有：name, desc, status, editable, 以及字典项status, value, name
	 * @return
	 * @throws GeneralException
	 * 	字典不存在时抛出异常
	 */
	DictionaryVo updateDictionary(DictionaryDto updates) throws GeneralException;
	
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
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionary(Long dict_id) throws GeneralException;
	
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
	 * @return
	 */
	Page<Dictionary> queryDictionaries(DictiCondiDto dictiCondiDTO);
	
	/**
	 * 删除字典
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionary(List<Long> dicts) throws GeneralException;
	
	/**
	 * 删除字典
	 * @return
	 * @throws GeneralException
	 *  字典不存在时抛出异常
	 */
	Boolean deleteDictionaryByObjects(List<Dictionary> dictionaries) throws GeneralException;
	
	/**
	 * 查询字典对象
	 * @return
	 */
	Dictionary queryOneDict(Long id);

	/**
	 * 根据字典ID和字典项ID删除字典项
	 * @return
	 */
	DictionaryVo removeItemByIds(DictionaryDto dictionaryDto) throws GeneralException;

	/**
	 * 获取字典类型
	 * @return 
	 */
	Map<String, List<String>> getDictTypes();

	DictionaryVo updateDictByUploadSingleFile(DictionaryDto dictionaryDto) throws Exception ;

	DictItemVO queryByKeyAndDictId(DictiCondiDto dictiCondiDto) throws Exception;

}
