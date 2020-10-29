package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 小强子大大
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {

}
