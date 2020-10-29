package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.*;
import com.xuecheng.manage_cms.service.PageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 小强子大大
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SysDictionaryDao sysDictionaryDao;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     *
     * @param page 页码，从1开始计数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if(queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //自定义条件查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage  cmsPage = new CmsPage();
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        /*BeanUtils.copyProperties(queryPageRequest, cmsPage);*/
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if(page <= 0) {
            page = 1;
        }
        if(size <= 0) {
            size = 10;
        }
        page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        //实现自定义条件查询并且分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        //数据列表
        cmsPageQueryResult.setList(all.getContent());
        //数据总记录数
        cmsPageQueryResult.setTotal(all.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
        return queryResponseResult;
    }

    /**
     * 新增页面
     *
     * @param cmsPage
     * @return
     */
    /*@Override
    public CmsPageResult add(CmsPage cmsPage) {
        //校验页面名称、站点id、页面webpath的唯一性
        //根据页面名称、站点id、页面webpath去cms_page集合查询，如果查到说明此页面已经存在，如果查询不到再继续添加。
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1 == null) {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        //添加失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }*/

    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        if(cmsPage == null) {
            //抛出非法参数异常
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //校验页面名称、站点id、页面webpath的唯一性
        //根据页面名称、站点id、页面webpath去cms_page集合查询，如果查到说明此页面已经存在，如果查询不到再继续添加。
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1 != null) {
            //页面已经存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        if(cmsPage1 == null) {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        //添加失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 根据页面id查询页面信息
     *
     * @param id
     * @return
     */
    @Override
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    /**
     * 修改页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPageResult update(String id, CmsPage cmsPage) {
        //根据id从数据库查询页面信息
        CmsPage cmsPage1 = this.getById(id);
        if(cmsPage1 != null) {
            //准备更新数据
            //更新模板id
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            cmsPage1.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            cmsPage1.setPageName(cmsPage.getPageName());
            //更新访问路径
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageRepository.save(cmsPage1);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage1);
        }
        //修改失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 根据id删除页面
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delete(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 根据id查询CMS配置信息
     *
     * @param id
     * @return
     */
    @Override
    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }

    /**
     * 页面静态化方法
     *
     * @param pageId
     * @return
     */
    @Override
    public String getPageHtml(String pageId) {
        //获取数据模型
        Map model = getModelByPageId(pageId);
        if(model == null) {
            //数据模型获取不到
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String template = getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(template)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //页面静态化
        String html = generateHtml(template, model);
        return html;
    }

    /**
     * 页面静态化
     * @param templateContent
     * @param model
     * @return
     */
    private String generateHtml(String templateContent, Map model) {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //加载模板
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateContent);
        //配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = null;
        try {
            template = configuration.getTemplate("template","utf-8");
            //静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取页面模板
     * @param pageId
     * @return
     */
    private String getTemplateByPageId(String pageId) {
        //获取页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null) {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取页面的模板id
        String templateId = cmsPage.getTemplateId();
        if(templateId == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //查询模板信息
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //从Grid中获取模板文件的内容
            //根据文件id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            //获取流中数据
            String s = null;
            try {
                s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }
        return null;
    }

    /**
     * 获取数据模型
     * @param pageId
     * @return
     */
    private Map getModelByPageId(String pageId) {
        //获取页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null) {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取页面的dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)) {
            //页面的dataUrl为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过restTemplate请求dataUrl获取数据模型
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }

    /**
     * 页面发布
     *
     * @param pageId
     * @return
     */
    @Override
    public ResponseResult post(String pageId) {
        //执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        //将静态化后的文件储存在GridFS中
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
        //通知MQ
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 向mq发送消息
     * @param pageId
     */
    private void sendPostPage(String pageId) {
        //得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //创建消息对象
        Map<String, String> msg = new HashMap<>();
        msg.put("pageId", pageId);
        //转成json串
        String jsonString = JSON.toJSONString(msg);
        //发送给mq
        String routingKey = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, routingKey, jsonString);
    }

    /**
     * 将静态化后的文件储存在GridFS中
     * @param pageId
     * @param htmlContent
     * @return
     */
    private CmsPage saveHtml(String pageId, String htmlContent) {
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try {
            //将htmlContent内容转化为输入流
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            //将html文件内容保存到GridFS中
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将html文件更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    /**
     * 根据字典分类type查询字典信息
     *
     * @param type
     * @return
     */
    @Override
    public SysDictionary findDictionaryByType(String type) {
        return sysDictionaryDao.findBydType(type);
    }

    /**
     * 保存页面，有了更新，没有再添加
     *
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPageResult save(CmsPage cmsPage) {
        //判断页面是否存在
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (one != null) {
            //进行更新
            return this.update(one.getPageId(), cmsPage);
        }
        return this.add(cmsPage);
    }

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //将页面信息存储到cms_page 集合中
        CmsPageResult save = this.save(cmsPage);
        if (!save.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //得到页面的id
        CmsPage cmsPageSave = save.getCmsPage();
        String pageId = cmsPageSave.getPageId();

        //执行页面发布（先静态化、保存GridFS，向MQ发送消息）
        ResponseResult post = this.post(pageId);
        if (!post.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //拼接页面Url= cmsSite.siteDomain+cmsSite.siteWebPath+ cmsPage.pageWebPath + cmsPage.pageName
        //取出站点id
        String siteId = cmsPageSave.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //页面url
        String pageUrl = cmsSite.getSiteDomain() + cmsSite.getSiteWebPath() + cmsPageSave.getPageWebPath() + cmsPageSave.getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 根据站点id查询站点信息
     * @param siteId
     * @return
     */
    public CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
