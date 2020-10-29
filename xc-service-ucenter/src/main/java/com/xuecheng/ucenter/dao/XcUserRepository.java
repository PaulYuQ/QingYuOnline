package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author 小强子大大
 */
public interface XcUserRepository extends JpaRepository<XcUser,String> {
    /**
     * 根据账 号查询用户信息
     * @param username
     * @return
     */
    XcUser findByUsername(String username);
}
