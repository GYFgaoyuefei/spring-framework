package com.eseasky.core.framework.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eseasky.core.framework.system.exception.GeneralException;
import com.eseasky.core.framework.system.models.entity.DictItem;
import com.eseasky.core.framework.system.models.entity.Dictionary;
import com.eseasky.core.framework.system.protocol.SystemParamPro;
import com.eseasky.core.framework.system.protocol.dto.DictiCondiDTO;
import com.eseasky.core.framework.system.protocol.dto.DictionaryDTO;
import com.eseasky.core.framework.system.protocol.vo.DictItemVO;
import com.eseasky.core.framework.system.protocol.vo.DictionaryVO;
import com.eseasky.core.framework.system.service.SystemDictService;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.MsgReturn;
import com.eseasky.global.utils.CheckUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "API - 系统参数模块", tags= {"系统参数模块接口"})
@RestController
public class SystemParamController  implements SystemParamPro {
	@Autowired
	SystemDictService systemDictService;
	
	@ApiOperation(value="根据类型查询有效的字典参数", notes="根据类型查询有效的字典参数")
	public ResponseEntity<MsgReturn<List<DictionaryVO>>> queryDict(@RequestBody DictionaryDTO dictionaryDTO){
		MsgReturn<List<DictionaryVO>> msgReturn = new MsgReturn<List<DictionaryVO>>();
		if (dictionaryDTO != null && dictionaryDTO.getType() != null && !"".equals(dictionaryDTO.getType())) {
			msgReturn.success(systemDictService.findValidDictByType(dictionaryDTO.getType()));
		} else {
			msgReturn.fail("缺少入参type");
		}
		return new ResponseEntity<MsgReturn<List<DictionaryVO>>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="分页查询字典参数", notes="分页查询字典参数")
	public ResponseEntity<MsgReturn<List<DictionaryVO>>> queryDictAdvance(@RequestBody DictiCondiDTO dictiCondiDTO) {
		MsgReturn<List<DictionaryVO>> msgReturn = new MsgReturn<List<DictionaryVO>>();
		Page<Dictionary> dictionaries = systemDictService.queryDictionaries(dictiCondiDTO);
		List<DictionaryVO> listDictionaryVO = dictionaries.getContent().stream().map(item -> {
			DictionaryVO dictionaryVO = new DictionaryVO();
			if (item != null) {
				BeanUtils.copyProperties(item, dictionaryVO);
				if ((dictiCondiDTO.getItemKey() != null && !CheckUtils.isEmpty(dictiCondiDTO.getItemKey().trim()))
						|| (dictiCondiDTO.getItemName() != null && !CheckUtils.isEmpty(dictiCondiDTO.getItemName().trim()))
						|| !CheckUtils.isEmpty(dictiCondiDTO.getItemStatus())) {
					List<DictItemVO> newDictItems = new ArrayList<>();
					for (DictItem dictItem : item.getDictItems()) {
						Boolean[] needFlag = {true, true, true};
						if (dictiCondiDTO.getItemKey() != null && !"".equals(dictiCondiDTO.getItemKey().trim())) {
							if (dictItem.getKey().contains(dictiCondiDTO.getItemKey().trim())) {
								needFlag[0] = true;
							} else {
								needFlag[0] = false;
							}
						}
						if (dictiCondiDTO.getItemName() != null && !"".equals(dictiCondiDTO.getItemName().trim())) {
							if (dictItem.getName().contains(dictiCondiDTO.getItemName().trim())) {
								needFlag[1] = true;
							} else {
								needFlag[1] = false;
							}
						}
						if (dictiCondiDTO.getItemStatus() != null && !"".equals(dictiCondiDTO.getItemStatus().trim())) {
							if (dictItem.getStatus().equals(dictiCondiDTO.getItemStatus())) {
								needFlag[2] = true;
							} else {
								needFlag[2] = false;
							}
						}
						if (needFlag[0] && needFlag[1] && needFlag[2]) {
							DictItemVO dictItemVO = new DictItemVO();
							BeanUtils.copyProperties(dictItem, dictItemVO);
							newDictItems.add(dictItemVO);
						}
					}
					dictionaryVO.setDictItems(newDictItems);
				}
			}
			return dictionaryVO;
		}).collect(Collectors.toList());
		msgReturn.success().setData(listDictionaryVO, MsgPageInfo.loadFromPageable(dictionaries));
		return new ResponseEntity<MsgReturn<List<DictionaryVO>>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="添加字典参数", notes="添加字典参数")
	public ResponseEntity<MsgReturn<DictionaryVO>> addDict(@RequestBody DictionaryDTO dictionaryDTO){
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		try {
			msgReturn.success(systemDictService.insertDictionary(dictionaryDTO));
		} catch (GeneralException e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="更新字典参数", notes="更新字典参数")
	public ResponseEntity<MsgReturn<DictionaryVO>> updateDict(@RequestBody DictionaryDTO dictionaryDTO){
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		try {
			msgReturn.success(systemDictService.updateDictionary(dictionaryDTO));
		} catch (GeneralException e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);

	}
	
	@ApiOperation(value="删除字典参数", notes="删除字典参数")
	public ResponseEntity<MsgReturn<List<Long>>> deleteDict(@RequestBody @ApiParam(value="字典ID列表") List<Long> ids){
		MsgReturn<List<Long>> msgReturn = new MsgReturn<List<Long>>();
		try {
			if (systemDictService.deleteDictionary(ids)) {
				msgReturn.success(ids);
			} else {
				msgReturn.fail("删除字典参数失败");
			}
		} catch (GeneralException e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<List<Long>>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="添加字典项", notes="添加字典项")
	public ResponseEntity<MsgReturn<DictionaryVO>> addDictItem(@RequestBody DictionaryDTO dictionaryDTO){
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		try {
			msgReturn.success(systemDictService.addItemToGroup(dictionaryDTO.getId(), dictionaryDTO.getDictItems()));
		} catch (GeneralException e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="根据条件删除字典项", notes="根据条件删除字典项")
	public ResponseEntity<MsgReturn<DictionaryVO>> removeItemByIds(@RequestBody DictionaryDTO dictionaryDTO){
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		try {
			msgReturn.success(systemDictService.removeItemByIds(dictionaryDTO));
		} catch (GeneralException e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="获取字典类型", notes="获取字典类型")
	public ResponseEntity<MsgReturn<Map<String, List<String>>>> getDictTypes() {
		MsgReturn<Map<String, List<String>>> msgReturn = new MsgReturn<Map<String, List<String>>>();
		msgReturn.success(systemDictService.getDictTypes());
		return new ResponseEntity<MsgReturn<Map<String, List<String>>>>(msgReturn, HttpStatus.OK);
	}

	@ApiOperation(value="上传获取excel文件更新字典项", notes="上传获取excel文件更新字典项")
	public ResponseEntity<MsgReturn<DictionaryVO>> updateDictByUploadSingleFile(DictionaryDTO dictionaryDTO) {
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		try {
			if (CheckUtils.isEmpty(dictionaryDTO.getFiles())) {
				msgReturn.fail("上传文件为空");
			} else {
				msgReturn.success(systemDictService.updateDictByUploadSingleFile(dictionaryDTO));
			}
		} catch (Exception e) {
			e.printStackTrace();
			msgReturn.fail(e.getMessage());
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<MsgReturn<DictionaryVO>> queryByTypeAndGroup(@RequestBody DictionaryDTO dictionaryDTO) {
		MsgReturn<DictionaryVO> msgReturn = new MsgReturn<DictionaryVO>();
		if (dictionaryDTO != null && dictionaryDTO.getType() != null && !"".equals(dictionaryDTO.getType()) && dictionaryDTO.getGroup() != null && !"".equals(dictionaryDTO.getGroup())) {
			String group = dictionaryDTO.getGroup();
			Dictionary dictionary = systemDictService.findValidDictByTypeAndGroup(dictionaryDTO.getType(), group);
			if(dictionary!=null) {
				DictionaryVO dictionaryVO = new DictionaryVO();
				BeanUtils.copyProperties(dictionary, dictionaryVO);
				msgReturn.success(dictionaryVO);
			}else {
				msgReturn.fail("暂无数据");
			}
		} else {
			msgReturn.fail("缺少入参type或者group");
		}
		return new ResponseEntity<MsgReturn<DictionaryVO>>(msgReturn, HttpStatus.OK);
	}
	
	@ApiOperation(value="系统参数模块表Model",notes="此接口不使用，只做输出Model数据结构")
	@PostMapping(value="/dict/testSwaggerApi", consumes="application/json")
	public void testSwaggerApi(@RequestBody Dictionary Dictionary
			,@RequestBody DictItem DictItem) {
		
	}
}