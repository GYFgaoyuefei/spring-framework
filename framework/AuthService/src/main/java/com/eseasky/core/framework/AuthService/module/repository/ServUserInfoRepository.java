package com.eseasky.core.framework.AuthService.module.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;

import java.util.Optional;

@Repository
public interface ServUserInfoRepository  extends PagingAndSortingRepository<ServUserInfo, Long> {


    @Override
    Optional<ServUserInfo> findById(Long aLong);

    Optional<ServUserInfo> findByUserName(String userName);

    @Override
    void deleteById(Long aLong);

    @Override
    Page<ServUserInfo> findAll(Pageable pageable);
}
