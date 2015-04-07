/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esa.infocontrol.data.ColumnMetaData;
import com.esa.infocontrol.data.DataArrayWrapper;
import com.esa.infocontrol.data.DataRow;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class BaseDataJDBC {

    private static Logger LOG = LoggerFactory.getLogger(BaseDataJDBC.class);

    public static DataArrayWrapper getList(String dataSourceName, String query, MapSqlParameterSource params) {
        DataSource dataSource = getDataSourceByName(dataSourceName);
        return getList(dataSource, query, params);
    }

    public static DataArrayWrapper getList(DataSource dataSource, String query, MapSqlParameterSource params) {
        LOG.debug("QUERY: {}", query);
        if (params != null) {
            LOG.debug("\tPARAMETERS: {}", params.getValues().toString());
        }
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, params);
        SqlRowSetMetaData md = rs.getMetaData();
        LOG.debug("\tCOLUMNS: {}", Arrays.toString(md.getColumnNames()));
       List<DataRow> dataList = new ArrayList<>();
        ColumnMetaData[] columnMetaData = new ColumnMetaData[md.getColumnCount()];
        for (int i = 1; i <= md.getColumnCount(); ++i) {
            columnMetaData[i - 1] = new ColumnMetaData(md.getColumnLabel(i), md.getColumnType(i));
        }
        while (rs.next()) {
            DataRow row = new DataRow(md.getColumnCount());
            for (int i = 1; i <= md.getColumnCount(); ++i) {
                row.add(rs.getString(i));
            }
            dataList.add(row);
        }
        return new DataArrayWrapper(dataList, columnMetaData);
    }
    
    static int updateData(String dataSourceName, String query, MapSqlParameterSource params) {
        DataSource dataSource = getDataSourceByName(dataSourceName);
        return updateData(dataSource, query, params);
    }
    
    static int updateData(DataSource dataSource, String query, MapSqlParameterSource params) {
        LOG.debug("QUERY: {}", query);
        if (params != null) {
            LOG.debug("\tPARAMETERS: {}", params.getValues().toString());
        }
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        int affectedRows = jdbcTemplate.update(query, params);
        return affectedRows;
    }
    
    public static DataSource getDataSourceByName(String name){
        DataSource ds = null;
        try {
            InitialContext ic = new InitialContext();
            ds = (DataSource)ic.lookup("jdbc/" + name);
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(BaseDataJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    
}
