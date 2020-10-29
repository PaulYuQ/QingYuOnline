package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 小强子大大
 */
@FeignClient(value = "XC-SERVICE-MANAGE-CMS")//指定远程调用的服务名
public interface CmsPageClient {
    /**
     *  根据页面的id查询页面的信息，远程调用cms请求数据
     * @param id
     * @return
     */
    @GetMapping("/cms/page/get/{id}")//标识远程调用的http方法类型
    CmsPage findCmsPageById(@PathVariable("id") String id);

    /**
     * 添加页面，用于课程预览
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/save")
    CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);

    /**
     * 页面一键发布
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/postPageQuick")
    CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);
}
