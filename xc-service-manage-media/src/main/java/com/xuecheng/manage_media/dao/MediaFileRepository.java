package com.xuecheng.manage_media.dao;

import com.xuecheng.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author 小强子大大
 */
public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
