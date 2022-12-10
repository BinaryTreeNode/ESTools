package com.bj.search.common.utils.esClient;

import com.bj.search.config.Env;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public class ClientConnection {
    static String esIp = Env.ES_IP;
    static int port = Integer.valueOf(Env.ES_PORT);
    static String userName = Env.USERNAME;
    static String password = Env.PASSWORD;

    public static RestHighLevelClient getClient(){
//	        RestHighLevelClient client = ESSecuredClient.initESClient(ip, port, userName, password);

        RestHighLevelClient clientNew = ESSecuredClient.initEsClientNew(esIp, port, userName, password);

        return clientNew;
    }

    public static void main (String [] args){

        getClient();
    }
}

