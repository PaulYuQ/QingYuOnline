package com.xuecheng.learning.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author 小强子大大
 */
@FeignClient(value= XcServiceList.XC_SERVICE_SEARCH)
public interface CourseSearchClient {

    /**
     * 根据课程计划id查询课程媒资
     * @param teachplanId
     * @return
     */
    @GetMapping("/search/course/getmedia/{teachplanId}")
    TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId);
}
