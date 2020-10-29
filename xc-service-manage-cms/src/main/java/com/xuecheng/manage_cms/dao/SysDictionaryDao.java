package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 小强子大大
 */
@Repository
public interface SysDictionaryDao extends MongoRepository<SysDictionary, String> {
    /**
     * 根 据字典分类查询字典信息
     * @param dType
     * @return
     */
    SysDictionary findBydType(String dType);
}
