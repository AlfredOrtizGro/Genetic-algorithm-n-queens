/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

/**
 *
 * @author Pandrosa
 */
public class FAvalue {
    private int generacion;
    private float FAValue;

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public float getFAValue() {
        return FAValue;
    }

    public void setFAValue(float FAValue) {
        this.FAValue = FAValue;
    }
    
    public FAvalue(int generacion, float favalue ){
        this.generacion=generacion;
        this.FAValue=favalue;
    }

    

  
    
  
    
}
