package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 小强子大大
 * @version 1.0
 * @date 2020年9月2日20:41:03
 * 接受页面查询的查询条件
 */
@Data
public class QueryPageRequest {
    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;
    /**
     * 页面id
     */
    @ApiModelProperty("页面id")
    private String pageId;
    /**
     * 页面名称
     */
    @ApiModelProperty("页面名称")
    private String pageName;
    /**
     * 别名
     */
    @ApiModelProperty("别名")
    private String pageAliase;
    /**
     * 模板id
     */
    @ApiModelProperty("模板id")
    private String templateId;

}
