/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data.jdbc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.esa.infocontrol.data.DataArrayWrapper;
import com.esa.infocontrol.data.DataRow;
import com.esa.infocontrol.utils.RequestParametersUtils;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class BaseDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseDAO.class);
	
	static final Map<String, String> queries = new HashMap<String, String>();
	static final String dataSourceName =  "InfoControl";
	static
	{
		queries.put("qry_user_by_username", "select * from users where username = :username");
		queries.put("qry_users", "select name, username, enabled from users");
		queries.put("qry_menu", "select * from menu where enabled =1 and authority in (:authorities) order by parent_id, seq asc");
		queries.put("qry_queries", "SELECT q.query, qds.datasource FROM queries q, queries_ds qds where q.name = qds.name and q.version = qds.version and q.name = :name and q.version = :version");
	}

	public static DataArrayWrapper getList(String queryName, int version, String id, Map<String, List<String>> queryParams){
                MapSqlParameterSource params = RequestParametersUtils.getQueryParameters(id, queryParams);
		return getList(queryName, version, id, params);
	}
        
        public static DataArrayWrapper getList(String queryName, int version, String id, MapSqlParameterSource params){
		String[] queryData = getQuery(queryName, version);
		return BaseDataJDBC.getList(queryData[0], queryData[1], params);
	}
	
	private static String[] getQuery(String queryName, int version) {
		String[] queryData = new String[2];
		if(queryName.charAt(0) == '$'){
			queryName = queryName.substring(1);
			LOG.debug("Getting query named: {}", queryName);
			MapSqlParameterSource params = new MapSqlParameterSource("name",queryName);
                        params.addValue("version", version);
			DataArrayWrapper data = BaseDAO.getList("qry_queries", version, null, params);
			StringBuilder sb = new StringBuilder();
			for(DataRow row: data.getDataArray()){
				sb.append(row.get(0));
				sb.append(" ");
			}
			queryData[0] = data.getDataArray().get(0).get(1);
			queryData[1] = sb.toString();
		}else{
			queryData[0] = dataSourceName;
			queryData[1] = queries.get(queryName);
		}
		System.out.println(Arrays.toString(queryData));
		return queryData;
	}

    public static void updateData(String queryName, int version, String id, Map<String, List<String>> queryParams) {
        MapSqlParameterSource params = RequestParametersUtils.getQueryParameters(id, queryParams);
        updateData(queryName, version, id, params);
    }
    
    public static void updateData(String queryName, int version, String id, MapSqlParameterSource params) {
        String[] queryData = getQuery(queryName, version);
        BaseDataJDBC.updateData(queryData[0], queryData[1], params);      
    }
    
}

