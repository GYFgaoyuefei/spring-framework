package com.eseasky.core.framework.AuthService.module.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.eseasky.core.framework.AuthService.module.model.AuthAccessToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.repository.AuthAccessTokenRepository;
import com.eseasky.core.framework.AuthService.module.repository.ServUserInfoRepository;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.ServUserInfoVO;


@Service
public class ServUserInfoServiceImpl implements ServUserInfoService {
	
	@Autowired
	ServUserInfoRepository servUserInfoRepository;
	@Autowired
	AuthAccessTokenRepository authAccessTokenRepository;

	@Override
	public ServUserInfo findByUserName(String userName) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> op = servUserInfoRepository.findByUserName(userName);
		  if(op.isPresent()){
			  ServUserInfo userInfo = op.get();
			  return userInfo;
          }
		return null;
	}

	@Override
	public ServUserInfoVO updateServUserInfo(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
		if(servUserInfoDTO.getId() != null ) {
			
			Optional<ServUserInfo> optional = servUserInfoRepository.findById(servUserInfoDTO.getId());
			if(optional.isPresent()) {
				if(!CheckUsername(servUserInfoDTO)) {
            		BeanUtils.copyProperties(optional.get(), servUserInfoVO);
            		ServUserInfo servUserInfo=optional.get();
            		BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
            		servUserInfo = servUserInfoRepository.save(servUserInfo);
            		BeanUtils.copyProperties(servUserInfo, servUserInfoVO);
            	}
            	else {
            		if(servUserInfoDTO.getUserName().equals(optional.get().getUserName())) {
            			BeanUtils.copyProperties(optional.get(), servUserInfoVO);
                		ServUserInfo servUserInfo=optional.get();
                		BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
                		servUserInfo = servUserInfoRepository.save(servUserInfo);
                		BeanUtils.copyProperties(servUserInfo, servUserInfoVO);
            		}else {
            			//throw new BusiException(BusiEnum.USERNAME_REPEATABLE);
            		}
            	}
				
			}else {
				//throw new BusiException(BusiEnum.NOT_FOUND_USER);
			}
			
		}else {
			//throw new BusiException(BusiEnum.USERINFO_NOID);
		}
		return servUserInfoVO;
	}

	@Override
	public ServUserInfoVO deleteServUserInfoById(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
		if(servUserInfoDTO.getId() != null ) {
			
			ServUserInfo servUserInfo = new ServUserInfo();
			
			 BeanUtils.copyProperties(servUserInfoDTO,servUserInfo);
			 servUserInfoRepository.deleteById(servUserInfo.getId());
			 
			 BeanUtils.copyProperties(servUserInfo,servUserInfoVO);
		}else {
			//throw new BusiException(BusiEnum.USERINFO_IDNOTNULL);
		}
		return servUserInfoVO;
	}
	
	@Override
	@Transactional
	public ServUserInfoVO addUserInfo(ServUserInfoDTO servUserInfoDTO) {
		ServUserInfo servUserInfo = new ServUserInfo();
		BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
		
		ckeckUserName(servUserInfo);
		
		ServUserInfo savedServUserInfo = new ServUserInfo();
		if (servUserInfo.getId() == null) {
			savedServUserInfo = servUserInfoRepository.save(servUserInfo);
		}else {
			//throw new BusiException(BusiEnum.USERINFO_NOID);
		}
		ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
		BeanUtils.copyProperties(savedServUserInfo, servUserInfoVO);
		// TODO Auto-generated method stub
		return servUserInfoVO;
	}
	
	private void ckeckUserName(ServUserInfo servUserInfo) {
		
		if (servUserInfoRepository.findByUserName(servUserInfo.getUserName()).isPresent()) {
			 //throw new BusiException(BusiEnum.USERNAME_REPEATABLE);
		}
	}

	@Override
	public Page<ServUserInfo> queryUserInfo(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ServUserInfo servUserInfo = new ServUserInfo();
		BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
		
		Pageable pageable = PageRequest.of(servUserInfoDTO.getPage(),servUserInfoDTO.getSize(),Sort.by(Direction.DESC, "id"));
		
		return servUserInfoRepository.findAll(new Specification<ServUserInfo>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<ServUserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();

				if (servUserInfo.getUserName() != null && !"".equals(servUserInfo.getUserName())) {
					predicates.add(criteriaBuilder.like(root.get("userName"),"%" + servUserInfo.getUserName() + "%"));
				}
				if (servUserInfo.getId() != null && !"".equals(servUserInfo.getId())) {
					predicates.add(criteriaBuilder.equal(root.get("id"), servUserInfo.getId()));
				}
				if (servUserInfo.getMobile() != null && !"".equals(servUserInfo.getMobile())) {
					predicates.add(criteriaBuilder.like(root.get("mobile"),"%" + servUserInfo.getMobile() + "%"));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

			}
		}, pageable);

		
	}


    @Override
    public ServUserInfoVO findById(ServUserInfoDTO servUserInfoDTO) {
        ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
        ServUserInfo servUserInfo = new ServUserInfo();
        BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
        if (servUserInfo.getId() != null) {
        	
            Optional<ServUserInfo> userInfo = servUserInfoRepository.findById(servUserInfo.getId());
            if (userInfo.isPresent() && userInfo.get() != null){
				BeanUtils.copyProperties(userInfo.get(), servUserInfoVO);
				AuthAccessToken authAccessToken = authAccessTokenRepository.findByUserName(userInfo.get().getUserName());
				if (authAccessToken==null)
					servUserInfoVO.setState("0");
				else
					servUserInfoVO.setState("1");
			}
        }
        return servUserInfoVO;
    }


    @Override
    public ServUserInfoVO updatePwd(ServUserInfoDTO servUserInfoDTO) {
        ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
        ServUserInfo servUserInfo = new ServUserInfo();
        BeanUtils.copyProperties(servUserInfoDTO, servUserInfo);
        if (servUserInfo.getId() != null && servUserInfo.getPassWord() != null) {
            Optional<ServUserInfo> userInfo = servUserInfoRepository.findById(servUserInfo.getId());
            ServUserInfo saveInfo;
            if (userInfo.isPresent() && (saveInfo = userInfo.get()) != null) {
                saveInfo.setPassWord(servUserInfo.getPassWord());
                try {
                    servUserInfoRepository.save(saveInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BeanUtils.copyProperties(saveInfo, servUserInfoVO);
            }
        }

        return servUserInfoVO;
    }

	@Override
	public boolean CheckUsername(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> ServUserInfo = servUserInfoRepository.findByUserName(servUserInfoDTO.getUserName());
		if(ServUserInfo.isPresent())
			return true;
		else
			return false;
	}

	@Override
	public ServUserInfoVO forceOffLine(ServUserInfoDTO servUserInfoDTO) {
		
		ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
		if(servUserInfoDTO.getUserName() != null && !"".equals(servUserInfoDTO.getUserName())) {
			
			ServUserInfo servUserInfo = new ServUserInfo();
			
			 BeanUtils.copyProperties(servUserInfoDTO,servUserInfo);
			 authAccessTokenRepository.deleteByUserName(servUserInfo.getUserName());
			 
			 BeanUtils.copyProperties(servUserInfo,servUserInfoVO);
		}else {
			//throw new BusiException(BusiEnum.USERINFO_IDNOTNULL);
		}
		return servUserInfoVO;
	}

}
