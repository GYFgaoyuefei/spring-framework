package com.eseasky.core.framework.AuthService.module.service;


import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.VRInfoVO;


public interface GroupService {

	GroupSaveVO saveGroup(GroupSaveDTO groupSaveDTO);

	GroupSaveVO updateGroup(GroupUpdateDTO groupUpdateDTO);

	GroupSaveVO deleteGroup(GroupUpdateDTO groupUpdateDTO);

	Page<GroupQueryVO> queryGroup(GroupQueryDTO groupQueryDTO);

	OrgGrantInfoVO grant(GroupGrantDTO groupGrantDTO);

	Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO);

	VRInfoVO reject(VRInfoDTO vRInfoDTO);

}
