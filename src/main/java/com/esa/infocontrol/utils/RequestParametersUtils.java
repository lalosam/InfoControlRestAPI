/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.utils;

import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author lobo
 */
public class RequestParametersUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(RequestParametersUtils.class);

    public static MapSqlParameterSource getQueryParameters(String id,
             Map<String, List<String>> queryParams) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        for(Map.Entry<String, List<String>> entry: queryParams.entrySet()){
            LOG.info(entry.getKey() + " -> " + entry.getValue());
            if(!entry.getKey().equalsIgnoreCase("id")){
                sqlParams.addValue(entry.getKey(), entry.getValue());
            }
        }
        if(id != null){
            sqlParams.addValue("id", id);
        }
        return sqlParams;
    }

}
