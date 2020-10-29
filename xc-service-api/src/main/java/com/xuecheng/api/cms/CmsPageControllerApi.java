package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author 小强子大大
 * @date 2020年9月2日20:49:32
 */
@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    /**
     * 页面查询
     * @param page 当前页
     * @param size 每页显示记录数
     * @param queryPageRequest 查询条件
     * @return 页面响应结果
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 新增页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("新增页面")
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据页面id查询页面信息
     * @param id
     * @return
     */
    @ApiOperation("根据页面id查询页面信息")
    CmsPage findById(String id);

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    @ApiOperation("修改页面")
    CmsPageResult edit(String id, CmsPage cmsPage);

    /**
     * 根据id删除页面
     * @param id
     * @return
     */
    @ApiOperation("删除页面")
    ResponseResult delete(String id);

    /**
     * 页面发布
     * @param pageId
     * @return
     */
    @ApiOperation("页面发布")
    ResponseResult post(String pageId);

    /**
     * 保存页面，有了更新，没有再添加
     * @param cmsPage
     * @return
     */
    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

    /**
     * 一键发布页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("一键发布页面")
    CmsPostPageResult postPageQuick(CmsPage cmsPage);

}
