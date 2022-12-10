package com.bj.search.config;

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
@Service
public class FieldConfig {

    @Autowired
    OutputProperties outputProperties;

    private Properties conf = new Properties();

    @PostConstruct
    public void FieldConfigInit() {
        FileInputStream confFile;
        try {
        	String filePath = outputProperties.getFieldconfig();
            confFile = new FileInputStream(filePath);
            conf.load(confFile);
            System.out.println("confFile=================="+filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return  conf.getProperty(key,"");
    }
}
