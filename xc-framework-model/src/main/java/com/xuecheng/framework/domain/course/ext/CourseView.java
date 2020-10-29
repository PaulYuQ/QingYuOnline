package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 小强子大大
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements java.io.Serializable {
    private CourseBase courseBase; //基础信息
    private CoursePic coursePic; //课程营销
    private CourseMarket courseMarket; //课程图片
    private TeachplanNode teachplanNode; //教学计划
}
