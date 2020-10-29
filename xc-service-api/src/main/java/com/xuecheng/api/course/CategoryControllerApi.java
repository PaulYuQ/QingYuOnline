package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 小强子大大
 */
@Api(value="课程分类管理",description="课程分类管理")
public interface CategoryControllerApi {
    /**
     * 查询分类
     * @return
     */
    @ApiOperation("查询分类")
    CategoryNode findList();
}
