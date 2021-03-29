/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RenéArnulfo
 */
public class TSerie {
    List <TFloatPoint> Valores;
    float  xMin;
    float  xMax;
    float  yMin;
    float  yMax;
    Color  UsedColor;
    int    OpPrint;
    
    public TSerie ( float[] X, float[] Y, Color Pinta){
        SetData(X,Y, Pinta);
    }
    
    public TSerie (Color Pinta, String Print){
       UsedColor = Pinta;
       Valores   = new LinkedList <TFloatPoint> ();
       if (Print.equalsIgnoreCase("NO"))          OpPrint   = 0;
       else if (Print.equalsIgnoreCase("GEN"))    OpPrint   = 1;
       else if (Print.equalsIgnoreCase("VAL"))    OpPrint   = 2;
       else if (Print.equalsIgnoreCase("GENVAL")) OpPrint   = 3;

    }
    
    public void Add(float x, float y){
     if (Valores.isEmpty()){
         xMin=x;
         xMax=x;
         yMin=y;
         yMax=y;
        }
     else {
         if (x < xMin) xMin = x;
         if (x > xMax) xMax = x;
         if (y < yMin) yMin = y;
         if (y > yMax) yMax = y;
     }  
     Valores.add(new TFloatPoint(x,y));
    }

    public void SetData( float[] X, float[] Y, Color Pinta){
       if(X.length != Y.length)
          throw new IllegalArgumentException("Los arreglos X y X deben de ser de la misma longitud");
        this.UsedColor = Pinta;
        Valores = new LinkedList <TFloatPoint> (); 
        for (int i = 0; i < Y.length; i++)
            Valores.add(new TFloatPoint(X[i],Y[i]));
     
        float[] xVals = getExtremeValues(X);
        xMin = xVals[0];
        xMax = xVals[1];
        float[] yVals = getExtremeValues(Y);
        yMin = yVals[0];
        yMax = yVals[1];
    }
    

    /**
     * Regresa los valores extremos de un arreglo de doubles, en la posicion [0] viene el valor mínimo y en [1] en valor máximo
     * @param Values arreglo con los valores a examinar 
     * @return arreglo con los valores mínimo y máximo de Values
     */
    private float[] getExtremeValues(float[] Values) {
        float min = Float.MAX_VALUE;
        float max = -min;
        for(int i = 0; i < Values.length; i++) {
            if(Values[i] < min) {
                min = Values[i];
            }
            if(Values[i] > max) {
                max = Values[i];
            }
        }
        return new float[] { min, max };
    }
}
