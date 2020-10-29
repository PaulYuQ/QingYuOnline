package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 小强子大大
 */
@Repository
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {

}