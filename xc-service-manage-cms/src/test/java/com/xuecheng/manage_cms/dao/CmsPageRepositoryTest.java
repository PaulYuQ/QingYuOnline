package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//@SpringBootTest会扫描com.xuecheng.manage_cms下的启动类，从而扫描启动类注解指定的bean，假如spring容器
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    //添加@Autowired，会从spring容器中获取被扫描到的bean。
    @Autowired
    CmsPageRepository cmsPageRepository;

    //查询所有数据
    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //设置相关参数查询数据
    @Test
    public void testFindPage() {
        //定义分页参数
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void testFindAllByExample() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //精确匹配
        /*cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5a962b52b00ffc514038faf7");*/
        cmsPage.setPageAliase("轮播");
        //条件匹配器 模糊匹配
        /*ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());*/
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
        //ExampleMatcher.GenericPropertyMatchers.startsWith() 前缀匹配
        //ExampleMatcher.GenericPropertyMatchers.caseSensitive() 区分大小写
        //定义Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    //添加
    @Test
    public void testInsert() {
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> list = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        list.add(cmsPageParam);
        cmsPage.setPageParams(list);

        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5f5209ccd8a01d46045cbab9");
    }

    //根据pageId查询
    @Test
    public void testFindById() {
        Optional<CmsPage> byId = cmsPageRepository.findById("5f520bdcd8a01d27a446b5a4");
        System.out.println(byId);
    }

    //修改
    @Test
    public void testUpdate() {
        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5f520bdcd8a01d27a446b5a4");
        if(optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }



}
