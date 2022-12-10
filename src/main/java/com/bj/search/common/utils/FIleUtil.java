package com.bj.search.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public class FIleUtil {
    public static List<String> readQuery(String queryFile){
        List<String> queryList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(queryFile), "utf-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String query = null;
        try {
            while ((query = br.readLine()) != null) {
                queryList.add(query);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  queryList;
    }

    public static List<Long> readInfoids(String queryFile){
        List<Long> queryList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(queryFile), "utf-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String query = null;
        try {
            while ((query = br.readLine()) != null) {
                queryList.add(Long.valueOf(query));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  queryList;
    }


}
