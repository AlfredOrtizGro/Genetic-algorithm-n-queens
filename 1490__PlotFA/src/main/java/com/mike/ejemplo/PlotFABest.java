/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import libs.ManejoArchivos;
import libs.ManejoER;

/**
 *
 * @author Pandrosa
 */
public class PlotFABest {
    ManejoArchivos IO = new ManejoArchivos();
    List<FAvalue> dataMax=new ArrayList<FAvalue>();
    List<FAvalue> dataMin=new ArrayList<FAvalue>();
    
  String getBestFA(String Estado,String optimod,int TOPGEN) {
       String bestFA= "";
        String [] Lineas    = IO.Read_Text_File(Estado);
        
        ManejoER Generacion = new ManejoER("====================Gene(.*)n:(\\d+)====================");
        ManejoER AdaptMax   = new ManejoER("Adapta(.*)n m(.*)xima:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        ManejoER AdaptAvr   = new ManejoER("Adaptaci(.*)n promedio:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        ManejoER AdaptMin   = new ManejoER("Adaptaci(.*)n m(.*)nima:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        int g = 0;
        
        if(TOPGEN==0){
            for (int i = 0, Gen = 0; i < Lineas.length; i++) {
            if (Generacion.ExistER(Lineas[i])){                   
                g = Integer.valueOf(Generacion.Grupo(2));
            }
            else if (AdaptMax.ExistER(Lineas[i])){                           
                dataMax.add(new FAvalue(g, Float.valueOf(AdaptMax.Grupo(3))));
                //System.out.println("Gen:"+AdaptMax.Grupo(3));
            }
            else if (AdaptMin.ExistER(Lineas[i])){
                dataMin.add(new FAvalue(g,Float.valueOf(AdaptMin.Grupo(3))));
               // System.out.println("Gen:"+AdaptMin.Grupo(3));
            }
            }
            
        }else if(TOPGEN>0){
          for (int i = 0, Gen = 0; i < Lineas.length; i++) {
            if(Generacion.ExistER(Lineas[i]) &&  !Generacion.Grupo(2).equals(TOPGEN) ){
                if (Generacion.ExistER(Lineas[i]))
                    g = Integer.valueOf(Generacion.Grupo(2));
                else if (AdaptMax.ExistER(Lineas[i]) ){
                    dataMax.add(new FAvalue(g,Float.valueOf(AdaptMax.Grupo(3))));
                    }
                else if (AdaptMin.ExistER(Lineas[i])){
                    dataMin.add(new FAvalue(g,Float.valueOf(AdaptMin.Grupo(3))));
                    }
            }
            }
        }
        
         if( optimod.equals("MAX")){
             bestFA=getTopFAMAX(dataMax);
            }else{
               bestFA=getTopFAMIN(dataMin);
            }
        
        
        return bestFA;
    }
  
//  
//    public static void main(String[] args) {
//        PlotFABest pb=new PlotFABest();
//        System.out.println(pb.getBestFA("C:\\Users\\PAI\\Documents\\ReposGitLab\\1490__PlotFA\\test\\State.txt","MAX",0));
//        
//        
//    }

    private String getTopFAMAX(List<FAvalue> dataMax) {
         String mayor="";
              sortMeasuresMajToMin(dataMax);
              mayor=" *Valor FAMAX: "+dataMax.get(0).getFAValue()+" en la GEN:"+dataMax.get(0).getGeneracion();
         return mayor;
    }

   private String getTopFAMIN(List<FAvalue> dataMin) {
         String menor="";
            sortMeasuresMinToMaj(dataMin);
              menor=" *Valor FAMIN: "+dataMin.get(0).getFAValue()+" en la GEN:"+dataMin.get(0).getGeneracion();
         return menor;
    }
    
    
    private void sortMeasuresMajToMin(List<FAvalue> dataList){
        Collections.sort(dataList, new Comparator<FAvalue>() {
            public int compare(FAvalue p1, FAvalue p2) {
		return new Float(p2.getFAValue()).compareTo(new Float (p1.getFAValue()));
            }
        });
    }
    
    /**
     * Ordena los resultados de menor a mayor
     */
    private void sortMeasuresMinToMaj(List<FAvalue> dataList){
        Collections.sort(dataList, new Comparator<FAvalue>() {
            public int compare(FAvalue p1, FAvalue p2) {
		return new Float(p1.getFAValue()).compareTo(new Float (p2.getFAValue()));
            }
        });
    }
    
}
