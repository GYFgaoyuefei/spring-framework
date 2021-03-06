package com.eseasky.core.framework.system.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eseasky.protocol.system.entity.VO.DictItemVO;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.eseasky.core.framework.system.protocol.dto.DictItemDto;
import com.eseasky.core.framework.system.protocol.dto.DictiCondiDto;
import com.eseasky.core.framework.system.protocol.dto.DictionaryDto;
import com.eseasky.core.framework.system.protocol.vo.DictItemVo;
import com.eseasky.core.framework.system.protocol.vo.DictionaryVo;
import com.eseasky.core.framework.system.service.SystemDictService;
import com.eseasky.global.utils.CheckUtils;
import com.eseasky.starter.dictionary.exception.ExceptionType;
import com.eseasky.starter.dictionary.exception.GeneralException;
import com.eseasky.starter.dictionary.module.model.DictItem;
import com.eseasky.starter.dictionary.module.model.Dictionary;
import com.eseasky.starter.dictionary.module.model.enums.DictionaryStatus;
import com.eseasky.starter.dictionary.module.model.local.DictionaryConditions;
import com.eseasky.starter.dictionary.module.service.SystemManagerService;


@Service
public class SystemDictServiceImpl implements SystemDictService {
	@Autowired
	SystemManagerService systemManagerService;
	
	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public DictionaryVo insertDictionary(DictionaryDto dictionaryDTO) throws GeneralException {
		Dictionary dictionary = dictionaryDTOToDictionary(dictionaryDTO);
		return dictionaryToDictionaryVO(systemManagerService.insertDictionary(dictionary));
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Dictionary addItemToGroup(String type, String group, List<DictItem> dictItems) throws GeneralException {
		return systemManagerService.addItemToGroup(type, group, dictItems);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public DictionaryVo addItemToGroup(Long dict_id, List<DictItemDto> dictItemDTOs) throws GeneralException {
		List<DictItem> dictItems = listDictItemDTOTolistDictItem(dictItemDTOs);
		return dictionaryToDictionaryVO(systemManagerService.addItemToGroup(dict_id, dictItems));
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Dictionary addItemToGroup(Dictionary dictionary, List<DictItem> dictItems) throws GeneralException {
		return systemManagerService.addItemToGroup(dictionary, dictItems);
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'findValidDictByTypeAndGroup'+#type+#group",unless="#result == null")
	public Dictionary findValidDictByTypeAndGroup(String type, String group) {
		return systemManagerService.findValidDictByTypeAndGroup(type, group);
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'findValidDictByType'+#type",unless="#result == null")
	public List<DictionaryVo> findValidDictByType(String type) {
		List<DictionaryVo> listDictionaryVO = new ArrayList<DictionaryVo> ();
		for(Dictionary dictionary : systemManagerService.findValidDictByType(type)) {
			DictionaryVo dictionaryVO = new DictionaryVo();
			BeanUtils.copyProperties(dictionary, dictionaryVO);
			listDictionaryVO.add(dictionaryVO);
		}
		return listDictionaryVO;
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'findDictByTypeAndStatus'+#type+#status",unless="#result == null")
	public List<Dictionary> findDictByTypeAndStatus(String type, String status) {
		return systemManagerService.findDictByTypeAndStatus(type, status);
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'findDictByTypeAndGroupAndStatus'+#type+#group+#status",unless="#result == null")
	public Dictionary findDictByTypeAndGroupAndStatus(String type, String group, String status) {
		return systemManagerService.findDictByTypeAndGroupAndStatus(type, group, status);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public DictionaryVo updateDictionary(DictionaryDto dictionaryDTO) throws GeneralException {
		Dictionary updates = dictionaryDTOToDictionary(dictionaryDTO);
		return dictionaryToDictionaryVO(systemManagerService.updateDictionary(updates));
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Boolean deleteDictionary(Dictionary dictionary) throws GeneralException {
		return systemManagerService.deleteDictionary(dictionary);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Boolean deleteDictionary(Long dict_id) throws GeneralException {
		return systemManagerService.deleteDictionary(dict_id);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Dictionary removeItemFromDict(Dictionary dictionary, List<Long> items) throws GeneralException {
		return systemManagerService.removeItemFromDict(dictionary, items);
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'queryDictionaries'+#dictiCondiDto",unless="#result == null")
	public Page<Dictionary> queryDictionaries(DictiCondiDto dictiCondiDto) {
		DictionaryConditions dictionaryConditions = new DictionaryConditions();
		BeanUtils.copyProperties(dictiCondiDto, dictionaryConditions);
		return systemManagerService.queryDictionaries(dictionaryConditions);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Boolean deleteDictionary(List<Long> dicts) throws GeneralException {
		return systemManagerService.deleteDictionary(dicts);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public Boolean deleteDictionaryByObjects(List<Dictionary> dictionaries) throws GeneralException {
		return systemManagerService.deleteDictionaryByObjects(dictionaries);
	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'queryOneDict'+#id",unless="#result == null")
	public Dictionary queryOneDict(Long id) {
		return systemManagerService.queryOneDict(id);
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public DictionaryVo removeItemByIds(DictionaryDto dictionaryDTO)  throws GeneralException {
		Dictionary dictionary = queryOneDict(dictionaryDTO.getId());
//		log.info(JSONObject.toJSON(dictionary));
		if (dictionary == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
//		List<Long> listItem = dictionary.getDictItems().stream().map(DictItem::getId).collect(Collectors.toList());
//		return dictionaryToDictionaryVO(removeItemFromDict(dictionary, listItem));
		return dictionaryToDictionaryVO(removeItemFromDict(dictionary, dictionaryDTO.getDictItemIds()));
	}
	
	private Dictionary dictionaryDTOToDictionary(DictionaryDto dictionaryDTO) {
		if (dictionaryDTO != null) {
			Dictionary dictionary = new Dictionary();
			BeanUtils.copyProperties(dictionaryDTO, dictionary);
			dictionary.setDictItems(listDictItemDTOTolistDictItem(dictionaryDTO.getDictItems()));
			return dictionary;
		} else {
			return null;
		}
	}

	private DictionaryVo dictionaryToDictionaryVO(Dictionary dictionary) {
		if (dictionary != null) {
			DictionaryVo dictionaryVO = new DictionaryVo();
			BeanUtils.copyProperties(dictionary, dictionaryVO);
			dictionaryVO.setDictItems(listDictItemTolistDictItemVO(dictionary.getDictItems()));
			return dictionaryVO;
		} else {
			return null;
		}
	}

	private List<DictItem> listDictItemDTOTolistDictItem(List<DictItemDto> listDictItemDTO) {
		if (listDictItemDTO != null) {
			List<DictItem> listDictItem = new ArrayList<DictItem> ();
			for (DictItemDto dictItemDTO: listDictItemDTO) {
				listDictItem.add(dictItemDTOToDictItem(dictItemDTO));
			}
			return listDictItem;
		} else {
			return null;
		}
	}

	private DictItem dictItemDTOToDictItem(DictItemDto dictItemDTO) {
		if (dictItemDTO != null) {
			DictItem dictItem = new DictItem();
			BeanUtils.copyProperties(dictItemDTO, dictItem);
			return dictItem;
		} else {
			return null;
		}
	}

	private List<DictItemVo> listDictItemTolistDictItemVO(List<DictItem> listDictItem) {
		if (listDictItem != null) {
			List<DictItemVo> listDictItemVO = new ArrayList<DictItemVo> ();
			for (DictItem dictItem: listDictItem) {
				listDictItemVO.add(dictItemToDictItemVO(dictItem));
			}
			return listDictItemVO;
		} else {
			return null;
		}
	}

	private DictItemVo dictItemToDictItemVO(DictItem dictItem) {
		if (dictItem != null) {
			DictItemVo dictItemVO = new DictItemVo();
			BeanUtils.copyProperties(dictItem, dictItemVO);
			return dictItemVO;
		} else {
			return null;
		}
	}

	@Override
	@Cacheable(value= {"sys_dictionary"}, key="'getDictTypes'",unless="#result == null")
	public Map<String, List<String>> getDictTypes() {
		Map<String, List<String>> map = new HashMap<String, List<String>> ();
		map.put("dictTypes", systemManagerService.getDictTypes());
		return map;
	}

	@Override
	@CacheEvict(value= {"sys_dict_item","sys_dictionary"}, allEntries=true)
	public DictionaryVo updateDictByUploadSingleFile(DictionaryDto dictionaryDTO) throws Exception {

		for (MultipartFile file: dictionaryDTO.getFiles()) {
			if (file.isEmpty() || file.getOriginalFilename() == null) {
				throw new Exception("上传文件有空文件");
			}
			if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
				throw new Exception(file.getOriginalFilename()+"文件格式不对");
			}
		}
		
	    List<DictItem> dictItems = new ArrayList<DictItem> ();
		for (MultipartFile file: dictionaryDTO.getFiles()) {
		    InputStream input = new ByteArrayInputStream(file.getBytes());
		    Workbook workbook = WorkbookFactory.create(input);
		    input.close();
		    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
		    	Sheet sheet = workbook.getSheetAt(i);
			    Iterator<Row> iteratorRow = sheet.rowIterator();
			    while (iteratorRow.hasNext()){
			    	Row row = iteratorRow.next();
			    	Cell cellKey = row.getCell(0);
			    	Cell cellValue = row.getCell(1);
					if (cellKey !=null && cellValue != null) {
						String strKey = getCellValue(cellKey);
						String strValue = getCellValue(cellValue);
						if(strKey.length()>255||strValue.length()>255) {
							throw new Exception("上传文件中有第"+ row.getRowNum() + "行有字段超过最大字数限制，请检查修改后重新上传");
						}
						if (!CheckUtils.isEmpty(strKey) && !CheckUtils.isEmpty(strValue)) {
					    	DictItem dictItem = new DictItem ();
					    	dictItem.setKey(strKey);
					    	dictItem.setValue(strValue);
					    	dictItem.setName(strValue);
					    	dictItem.setStatus(DictionaryStatus.VALID.getValue());
					    	dictItems.add(dictItem);
						}
					}			    	
			    }
		    }	
		}
		
	    Dictionary dictionary = dictionaryDTOToDictionary(dictionaryDTO);
	    dictionary.setDictItems(dictItems);
	    if (dictionary.getId() != null) {
		    return dictionaryToDictionaryVO(systemManagerService.updateDictionary(dictionary));
	    } else {
		    return dictionaryToDictionaryVO(systemManagerService.insertDictionary(dictionary));
	    }

	}

	@Override
	@Cacheable(value= {"sys_dict_item","sys_dictionary"}, key="'queryByKeyAndDictId'+#dictiCondiDTO.group+#dictiCondiDTO.type+#dictiCondiDTO.itemKey",unless="#result == null")
	public DictItemVO queryByKeyAndDictId(DictiCondiDto dictiCondiDTO) throws Exception {
		if(StringUtils.isBlank(dictiCondiDTO.getType())||StringUtils.isBlank(dictiCondiDTO.getGroup())||StringUtils.isBlank(dictiCondiDTO.getItemKey())){
			throw new Exception("字典类型，字典分组，映射Key不能为空");
		}
		DictItem dictItem = systemManagerService.getDictItem(dictiCondiDTO.getType(), dictiCondiDTO.getGroup(), dictiCondiDTO.getItemKey());
		if(dictItem!=null){
			DictItemVO dictItemVO = new DictItemVO();
			BeanUtils.copyProperties(dictItem,dictItemVO);
			return dictItemVO;
		}
		return null;
	}

	private String getCellValue(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
        case STRING:
            strCell = cell.getStringCellValue();
            break;
        case NUMERIC:
//            strCell = String.valueOf(cell.getNumericCellValue());
        	cell.setCellType(CellType.STRING);
        	strCell = cell.getStringCellValue();
            break;
        case BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }

        if (strCell == null) {
            return "";
        }

        return strCell;
	}

}
