package com.bj.search.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class FieldConfig {

    @Autowired
    OutputProperties outputProperties;

    private Properties conf = new Properties();

    @PostConstruct
    public void FieldConfigInit() throws IOException {
        FileInputStream confFile;
        String filePath = outputProperties.getFieldconfig();
        confFile = new FileInputStream(filePath);
        conf.load(confFile);
        log.info("confFile=================="+filePath);
        confFile.close();
    }

    public String getProperty(String key){
        return  conf.getProperty(key,"");
    }
}
