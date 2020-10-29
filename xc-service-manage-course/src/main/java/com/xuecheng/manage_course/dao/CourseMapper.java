package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小强子大大
 */
@Mapper
public interface CourseMapper {
    /**
     * 根据课程id查询课程
     * @param id
     * @return
     */
   CourseBase findCourseBaseById(String id);

    /**
     * 查询课程列表
     * @return
     */
   Page<CourseBase> findCourseList();

    /**
     * 分页查询课程列表
     * @param courseListRequest
     * @return
     */
   Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
