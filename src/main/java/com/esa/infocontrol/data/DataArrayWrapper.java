/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data;


import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class DataArrayWrapper {

    // Required by JAXB
    public DataArrayWrapper() {
    }
    
    List<DataRow> dataArray = null;
    ColumnMetaData[] columnMetaData = null;

    public DataArrayWrapper(List<DataRow> dataArray, ColumnMetaData[] columnMetaData) {
        super();
        this.dataArray = dataArray;
        this.columnMetaData = columnMetaData;
    }
    
    @XmlElement(name = "rows")
    public List<DataRow> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<DataRow> dataArray) {
        this.dataArray = dataArray;
    }

    public ColumnMetaData[] getColumnMetaData() {
        return columnMetaData;
    }

    public void setColumnMetaData(ColumnMetaData[] columnMetaData) {
        this.columnMetaData = columnMetaData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ColumnMetaData column: columnMetaData){
            sb.append(column.toString());
            sb.append("\t");
        }
        sb.append("\n");
        for(DataRow row: dataArray){
             sb.append(row.toString());
            sb.append("\n");
        }
        sb.append("\n");     
        return sb.toString();
    }
    
    public String pretty(){
        int[] maxColumnWidths = new int[columnMetaData.length];
        for(int i = 0; i < maxColumnWidths.length; ++i){
            maxColumnWidths[i] = columnMetaData[i].toString().length();
            for(DataRow row: dataArray){
                int size = row.get(i).length();
                if(size > maxColumnWidths[i]){
                    maxColumnWidths[i] = size;
                }
            }
        }
        System.out.println(Arrays.toString(maxColumnWidths));
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < maxColumnWidths.length; ++i){
            sb.append(columnMetaData[i].pretty(maxColumnWidths[i]));
            sb.append("\t");
        }
        sb.append("\n");
        for(DataRow row: dataArray){
             sb.append(row.pretty(maxColumnWidths));
            sb.append("\n");
        }
        sb.append("\n");     
        return sb.toString();
    }
    
    

}
