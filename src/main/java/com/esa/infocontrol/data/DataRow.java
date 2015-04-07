/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author lobo
 */
public class DataRow {

    List<String> data;

    public DataRow() {
        data = new ArrayList<>();
    }

    public DataRow(int size) {
        data = new ArrayList<>(size);
    }

    @XmlElement(name = "values")
    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void add(String value) {
        if(value == null){
            value = "NULL";
        }
        data.add(value);
    }

    public String get(int index) {
        return data.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String field : data) {
            sb.append(field);
            sb.append("\t");
        }
        return sb.toString();
    }

    public String pretty(int[] maxColumnWidths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxColumnWidths.length; ++i) {
            sb.append(String.format("%1$-" + maxColumnWidths[i] + "s", data.get(i)));
            sb.append("\t");
        }
        return sb.toString();
    }

}
