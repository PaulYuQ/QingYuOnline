package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 *
 * @author 余强
 * @date 2020年10月3日19:51:52
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
