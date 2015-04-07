/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lobo
 */
@XmlRootElement
public class BasicDTO {
    public int step;
    public String[] strArray;

    public BasicDTO() {
    }

    public BasicDTO(int step) {
        this.step = step;
        strArray = new String[7]; 
        strArray[0] = "A";
        strArray[1] = "B";
        strArray[2] = "C";
        strArray[3] = "D";
        strArray[4] = "E";
        strArray[5] = "F";
        strArray[6] = "G";
    }
    
    
}
