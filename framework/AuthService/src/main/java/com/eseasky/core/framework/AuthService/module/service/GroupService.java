package com.eseasky.core.framework.AuthService.module.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.AuPowerGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.QueryGroupDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.VRInfoVO;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;


public interface GroupService {

	GroupSaveVO addGroup(GroupSaveDTO groupSaveDTO);

	List<GroupQueryVO> queryGroup(QueryGroupDTO groupQueryDTO);

	void deleteGroup(QueryGroupDTO groupUpdateDTO);

	UserGrantInfoVO grantByGroup(GroupGrantDTO groupGrantDTO);

	UserGrantInfoVO transOUGToUGIVO(OrgUserGranted orgUserGranted);


}
