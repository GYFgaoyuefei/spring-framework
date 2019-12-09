package com.eseasky.core.framework.AuthService.module.service;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantedItemVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgUserGrantedVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;

public interface GrantService {

	Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO);

	OrgGrantInfoVO grant(OrgGrantInfoDTO orgGrantInfoDTO);

	OrgGrantInfoVO updateGrant(OrgUpdateGrantDTO orgUpdateGrantDTO);

	Page<OrgGrantedItemVO> queryGranted(OrgQueryGrantDTO orgQueryGrantDTO);

	Page<OrgUserGrantedVO> queryOrgUserGranted(OrgQueryGrantDTO orgQueryGrantDTO);

	OrgGrantInfoVO deleteGrant(OrgUpdateGrantDTO orgUpdateGrantDTO);

}
