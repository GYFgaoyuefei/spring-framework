package com.eseasky.core.framework.system.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.eseasky.core.framework.system.protocol.dto.DictItemDTO;
import com.eseasky.core.framework.system.protocol.dto.DictiCondiDTO;
import com.eseasky.core.framework.system.protocol.dto.DictionaryDTO;
import com.eseasky.core.framework.system.protocol.vo.DictItemVO;
import com.eseasky.core.framework.system.protocol.vo.DictionaryVO;
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
	public DictionaryVO insertDictionary(DictionaryDTO dictionaryDTO) throws GeneralException {
		Dictionary dictionary = dictionaryDTOToDictionary(dictionaryDTO);
		return dictionaryToDictionaryVO(systemManagerService.insertDictionary(dictionary));
	}

	@Override
	public Dictionary addItemToGroup(String type, String group, List<DictItem> dictItems) throws GeneralException {
		return systemManagerService.addItemToGroup(type, group, dictItems);
	}

	@Override
	public DictionaryVO addItemToGroup(Long dict_id, List<DictItemDTO> dictItemDTOs) throws GeneralException {
		List<DictItem> dictItems = listDictItemDTOTolistDictItem(dictItemDTOs);
		return dictionaryToDictionaryVO(systemManagerService.addItemToGroup(dict_id, dictItems));
	}

	@Override
	public Dictionary addItemToGroup(Dictionary dictionary, List<DictItem> dictItems) throws GeneralException {
		return systemManagerService.addItemToGroup(dictionary, dictItems);
	}

	@Override
	public Dictionary findValidDictByTypeAndGroup(String type, String group) {
		return systemManagerService.findValidDictByTypeAndGroup(type, group);
	}

	@Override
	public List<DictionaryVO> findValidDictByType(String type) {
		List<DictionaryVO> listDictionaryVO = new ArrayList<DictionaryVO> ();
		for(Dictionary dictionary : systemManagerService.findValidDictByType(type)) {
			DictionaryVO dictionaryVO = new DictionaryVO();
			BeanUtils.copyProperties(dictionary, dictionaryVO);
			listDictionaryVO.add(dictionaryVO);
		}
		return listDictionaryVO;
	}

	@Override
	public List<Dictionary> findDictByTypeAndStatus(String type, String status) {
		return systemManagerService.findDictByTypeAndStatus(type, status);
	}

	@Override
	public Dictionary findDictByTypeAndGroupAndStatus(String type, String group, String status) {
		return systemManagerService.findDictByTypeAndGroupAndStatus(type, group, status);
	}

	@Override
	public DictionaryVO updateDictionary(DictionaryDTO dictionaryDTO) throws GeneralException {
		Dictionary updates = dictionaryDTOToDictionary(dictionaryDTO);
		return dictionaryToDictionaryVO(systemManagerService.updateDictionary(updates));
	}

	@Override
	public Boolean deleteDictionary(Dictionary dictionary) throws GeneralException {
		return systemManagerService.deleteDictionary(dictionary);
	}

	@Override
	public Boolean deleteDictionary(Long dict_id) throws GeneralException {
		return systemManagerService.deleteDictionary(dict_id);
	}

	@Override
	public Dictionary removeItemFromDict(Dictionary dictionary, List<Long> items) throws GeneralException {
		return systemManagerService.removeItemFromDict(dictionary, items);
	}

	@Override
	public Page<Dictionary> queryDictionaries(DictiCondiDTO dictiCondiDTO) {
		DictionaryConditions dictionaryConditions = new DictionaryConditions();
		BeanUtils.copyProperties(dictiCondiDTO, dictionaryConditions);
		return systemManagerService.queryDictionaries(dictionaryConditions);
	}

	@Override
	public Boolean deleteDictionary(List<Long> dicts) throws GeneralException {
		return systemManagerService.deleteDictionary(dicts);
	}

	@Override
	public Boolean deleteDictionaryByObjects(List<Dictionary> dictionaries) throws GeneralException {
		return systemManagerService.deleteDictionaryByObjects(dictionaries);
	}

	@Override
	public Dictionary queryOneDict(Long id) {
		return systemManagerService.queryOneDict(id);
	}

	@Override
	public DictionaryVO removeItemByIds(DictionaryDTO dictionaryDTO)  throws GeneralException {
		Dictionary dictionary = queryOneDict(dictionaryDTO.getId());
//		log.info(JSONObject.toJSON(dictionary));
		if (dictionary == null) {
			throw new GeneralException(ExceptionType.SYSTEMNOEXISTSDICT);
		}
//		List<Long> listItem = dictionary.getDictItems().stream().map(DictItem::getId).collect(Collectors.toList());
//		return dictionaryToDictionaryVO(removeItemFromDict(dictionary, listItem));
		return dictionaryToDictionaryVO(removeItemFromDict(dictionary, dictionaryDTO.getDictItemIds()));
	}
	
	private Dictionary dictionaryDTOToDictionary(DictionaryDTO dictionaryDTO) {
		if (dictionaryDTO != null) {
			Dictionary dictionary = new Dictionary();
			BeanUtils.copyProperties(dictionaryDTO, dictionary);
			dictionary.setDictItems(listDictItemDTOTolistDictItem(dictionaryDTO.getDictItems()));
			return dictionary;
		} else {
			return null;
		}
	}

	private DictionaryVO dictionaryToDictionaryVO(Dictionary dictionary) {
		if (dictionary != null) {
			DictionaryVO dictionaryVO = new DictionaryVO();
			BeanUtils.copyProperties(dictionary, dictionaryVO);
			dictionaryVO.setDictItems(listDictItemTolistDictItemVO(dictionary.getDictItems()));
			return dictionaryVO;
		} else {
			return null;
		}
	}

	private List<DictItem> listDictItemDTOTolistDictItem(List<DictItemDTO> listDictItemDTO) {
		if (listDictItemDTO != null) {
			List<DictItem> listDictItem = new ArrayList<DictItem> ();
			for (DictItemDTO dictItemDTO: listDictItemDTO) {
				listDictItem.add(dictItemDTOToDictItem(dictItemDTO));
			}
			return listDictItem;
		} else {
			return null;
		}
	}

	private DictItem dictItemDTOToDictItem(DictItemDTO dictItemDTO) {
		if (dictItemDTO != null) {
			DictItem dictItem = new DictItem();
			BeanUtils.copyProperties(dictItemDTO, dictItem);
			return dictItem;
		} else {
			return null;
		}
	}

	private List<DictItemVO> listDictItemTolistDictItemVO(List<DictItem> listDictItem) {
		if (listDictItem != null) {
			List<DictItemVO> listDictItemVO = new ArrayList<DictItemVO> ();
			for (DictItem dictItem: listDictItem) {
				listDictItemVO.add(dictItemToDictItemVO(dictItem));
			}
			return listDictItemVO;
		} else {
			return null;
		}
	}

	private DictItemVO dictItemToDictItemVO(DictItem dictItem) {
		if (dictItem != null) {
			DictItemVO dictItemVO = new DictItemVO();
			BeanUtils.copyProperties(dictItem, dictItemVO);
			return dictItemVO;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, List<String>> getDictTypes() {
		Map<String, List<String>> map = new HashMap<String, List<String>> ();
		map.put("dictTypes", systemManagerService.getDictTypes());
		return map;
	}

	@Override
	public DictionaryVO updateDictByUploadSingleFile(DictionaryDTO dictionaryDTO) throws Exception {

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
	public DictItemVO queryByKeyAndDictId(DictiCondiDTO dictiCondiDTO) throws Exception {
		if(StringUtils.isBlank(dictiCondiDTO.getType())||StringUtils.isBlank(dictiCondiDTO.getGroup())||StringUtils.isBlank(dictiCondiDTO.getItemKey())){
			throw new Exception("字典类型，字典分组，映射Key不能为空");
		}
		Dictionary dictionary = systemManagerService.findDictByTypeAndGroup(dictiCondiDTO.getType(), dictiCondiDTO.getGroup());
		if(dictionary==null ){
			throw new Exception("字典值不存在");
		}
		List<DictItem> dictItems = dictionary.getDictItems();
		for (DictItem dictItem : dictItems) {
			if (dictItem.getKey().equals(dictiCondiDTO.getItemKey())){
				DictItemVO dictItemVO = new DictItemVO();
				BeanUtils.copyProperties(dictItem, dictItemVO);
				return  dictItemVO;
			}
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
