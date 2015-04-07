/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data;

/**
 *
 * @author lobo
 */
public class ColumnMetaData {

    private int columnType;
    private String columnName;

    public ColumnMetaData() {}

    public ColumnMetaData(String columnName, int columnType) {
        this.columnType = columnType;
        this.columnName = columnName;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }

    public String pretty(int size) {
        return String.format("%1$-" + size + "s", toString());
    }
}
