package com.eseasky.core.framework.AuthService.module.service;


import java.util.List;


import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.QueryGroupDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;


public interface GroupService {

	GroupSaveVO addGroup(GroupSaveDTO groupSaveDTO);

	List<GroupQueryVO> queryGroup(QueryGroupDTO groupQueryDTO);

	void deleteGroup(QueryGroupDTO groupUpdateDTO);

	UserGrantInfoVO grantByGroup(GroupGrantDTO groupGrantDTO);

	UserGrantInfoVO transOUGToUGIVO(OrgUserGranted orgUserGranted);


}
