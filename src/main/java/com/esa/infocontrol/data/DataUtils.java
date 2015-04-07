/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataUtils {
	
	static Logger LOG = LoggerFactory.getLogger(DataUtils.class);
	
	public static void printDataWrapper(DataArrayWrapper wrapper, boolean printMetaData){
		if(printMetaData){
			printMetaDataArray(wrapper.getColumnMetaData());
		}
		List<DataRow> data = wrapper.getDataArray();
		System.out.println("ROWS: " + data.size());
		for(DataRow row : data){
			LOG.debug(row.toString());
		}
	}
	
	public static void printMetaDataMap(Map<String, ColumnMetaData> metaData){
		for(ColumnMetaData column: metaData.values()){
			System.out.println(column.toString());
		}
	}
	
	public static void printMetaDataArray(ColumnMetaData[] metaData){
		for(ColumnMetaData column: metaData){
			LOG.debug(column.toString());
		}
	}
	

}

