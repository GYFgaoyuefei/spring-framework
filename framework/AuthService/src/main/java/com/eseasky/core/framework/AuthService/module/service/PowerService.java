package com.eseasky.core.framework.AuthService.module.service;


import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.AuPowerGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.protocol.auth.entity.VO.VRInfoVO;


public interface PowerService {

	PowerSaveVO createPower(PowerSaveDTO groupSaveDTO);

	PowerSaveVO updatePower(PowerUpdateDTO groupUpdateDTO);

	PowerSaveVO deletePower(PowerUpdateDTO groupUpdateDTO);

	Page<PowerQueryVO> queryPower(PowerQueryDTO groupQueryDTO);

	OrgGrantInfoVO grant(AuPowerGrantDTO groupGrantDTO);

	Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO);

	VRInfoVO reject(VRInfoDTO vRInfoDTO);


}
