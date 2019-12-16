package com.eseasky.core.framework.AuthService.module.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfosDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantedItemVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;

@Valid
public interface GrantService {

	Page<ResoureQueryVO> queryResoureItem(ResoureQueryDTO resoureQueryDTO);

	
	GrantInfoVO grant(@Valid OrgGrantInfoDTO orgGrantInfoDTO);

	OrgGrantInfoVO updateGrant(OrgUpdateGrantDTO orgUpdateGrantDTO);

	Page<OrgGrantedItemVO> queryGranted(OrgQueryGrantDTO orgQueryGrantDTO);

	Page<ResoureQueryVO> queryOrgUserGranted(OrgQueryGrantDTO orgQueryGrantDTO);

	OrgGrantInfoVO deleteGrant(OrgUpdateGrantDTO orgUpdateGrantDTO);

	List<GrantInfoVO> grant(OrgGrantInfosDTO orgGrantInfoDTOs);

	void deleteByUser(String userName);

}
