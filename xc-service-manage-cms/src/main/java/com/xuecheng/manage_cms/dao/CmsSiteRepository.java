package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 小强子大大
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
