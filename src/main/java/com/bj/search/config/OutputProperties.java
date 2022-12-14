package com.bj.search.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */

@Component
@ConfigurationProperties("testfile")
//@PropertySource(value = "output.properties", ignoreResourceNotFound = false, encoding = "UTF-8")
public class OutputProperties {

    /**
     * 读取的原始文件
     */
    private String sourcefile;

    /**
     * 保存构造的query
     */
    private String queryfile;


    public String getFieldconfig() {
        return fieldconfig;
    }

    public void setFieldconfig(String fieldconfig) {
        this.fieldconfig = fieldconfig;
    }

    private String fieldconfig;


    public String getSourcefile() {
        return sourcefile;
    }

    public void setSourcefile(String sourcefile) {
        this.sourcefile = sourcefile;
    }

    public String getQueryfile() {
        return queryfile;
    }

    public void setQueryfile(String queryfile) {
        this.queryfile = queryfile;
    }
}
