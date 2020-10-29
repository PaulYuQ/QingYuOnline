package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 小强子大大
 */
public interface PageService {
    /**
     * 条件查询
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 新增页面
     *
     * @param cmsPage
     * @return
     */
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据页面id查询页面信息
     * @param id
     * @return
     */
    CmsPage getById(String id);

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    CmsPageResult update(String id, CmsPage cmsPage);

    /**
     * 根据id删除页面
     * @param id
     * @return
     */
    ResponseResult delete(String id);

    /**
     *  根据id查询CMS配置信息
     * @param pageId
     * @return
     */
    CmsConfig getConfigById(String pageId);

    /**
     * 页面静态化方法
     * @param id
     * @return
     */
    String getPageHtml(String id);

    /**
     * 页面发布
     * @param pageId
     * @return
     */
    ResponseResult post(String pageId);

    /**
     * 根据字典分类type查询字典信息
     * @param type
     * @return
     */
    SysDictionary findDictionaryByType(String type);

    /**
     * 保存页面，有了更新，没有再添加
     *
     * @param cmsPage
     * @return
     */
    CmsPageResult save(CmsPage cmsPage);

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
