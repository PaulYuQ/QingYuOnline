package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 小强子大大
 */
public interface CoursePicRepository extends JpaRepository<CoursePic, String > {
    /**
     * 当返回值大于0，删除成功
     * @param courseId
     * @return
     */
    long deleteByCourseid(String courseId);
}
