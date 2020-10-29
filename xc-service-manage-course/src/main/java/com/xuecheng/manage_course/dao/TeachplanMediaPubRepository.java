package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 小强子大大
 */
public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
    /**
     * 根据 课程id删除记录
     * @param courseId
     * @return
     */
    long deleteByCourseId(String courseId);
}
