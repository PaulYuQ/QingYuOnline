package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 小强子大大
 */
@Mapper
public interface XcMenuMapper {
    /**
     * 根据用户id查 询用户的权限
     * @param userid
     * @return
     */
    List<XcMenu> selectPermissionByUserId(String userid);
}
