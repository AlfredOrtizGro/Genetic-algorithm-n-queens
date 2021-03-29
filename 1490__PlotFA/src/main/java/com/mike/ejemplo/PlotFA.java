/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;
import libs.RUN;


/**
 *
 * @author RenéArnulfo
 */
public class PlotFA {
  String  FWORK;
  String  FSTATE;
  String  HEAD; 
  boolean PLOTMAX;
  String  PMAXTEXT; 
  boolean PLOTPROM; 
  String  PPROMTEXT;
  boolean PLOTMIN;
  String  PMINTEXT;
  String  LABELS;
  String  FIMGFA;
  String NOTES;
  int TOPGEN;
  ManejoArchivos IO;
  boolean VIEWIFAZ; //Esta bandera nos permite ver la interfaz grafica
  String SAVEBOX;
  Boolean PBESTFA;
  String PSAVEBOX;
  String OPTIMOD;
  
  
    /**
     * @param args the command line arguments
     */
    PlotFA(String[] args) {
       IO                  = new ManejoArchivos(); 
       MyListArgs  Param = new MyListArgs(args);
       String   ConfigFile = Param.ValueArgsAsString( "-CONFIG"  ,  ""  );//Archivo de configuración
       if ( ! ConfigFile.equals("") )
           Param.AddArgsFromFile(ConfigFile);
       
       String   Sintaxis  = " -FWORK:str [-FSTATE:str] [-TOPGEN:int] [-PBESTFA:bool] [-OPTIMOD:str] [-HEAD:str] [-FIMGFA:str] [-VIEWIFAZ:bool] [-NOTES:str] [-LABELS:str] [-SAVEBOX:str -PARAMBOX:str] [-PLOTMAX:bool [-PMAXTEXT:NO:GEN:VAL:GENVAL]] [-PLOTPROM:bool [-PPROMTEXT:NO:GEN:VAL:GENVAL] ][-test:str] [-PLOTMIN:bool [-PMINTEXT:NO:GEN:VAL:GENVAL]] " ;  
       MySintaxis Review  = new MySintaxis( Sintaxis , Param);
       
       //Forzosos
       FWORK     = Param.ValueArgsAsString ( "-FWORK"    , ""      );
       FSTATE    = Param.ValueArgsAsString ( "-FSTATE"   , "default");
       HEAD      = Param.ValueArgsAsString ( "-HEAD"     , ">--ö---<" );
       PLOTMAX   = Param.ValueArgsAsBoolean( "-PLOTMAX"  , true    );
       PMAXTEXT  = Param.ValueArgsAsString ( "-PMAXTEXT" , "GENVAL");
       PLOTPROM  = Param.ValueArgsAsBoolean( "-PLOTPROM" , true    );
       PPROMTEXT = Param.ValueArgsAsString ( "-PPROMTEXT", "GENVAL");
       PLOTMIN   = Param.ValueArgsAsBoolean( "-PLOTMIN"  , true    );
       PMINTEXT  = Param.ValueArgsAsString ( "-PMINTEXT" , "GENVAL");
       PBESTFA   = Param.ValueArgsAsBoolean( "-PBESTFA"   , true);
       LABELS      = Param.ValueArgsAsString ( "-LABELS" , "-NELITE,-KTORNEO,-PMUT,-XPROB,-NI");
       FIMGFA    = Param.ValueArgsAsString ( "-FIMGFA"   , "ImgGenGen.png");
       TOPGEN    = Param.ValueArgsAsInteger( "-TOPGEN"   , 0);
       VIEWIFAZ  = Param.ValueArgsAsBoolean( "-VIEWIFAZ" , true);
       NOTES     = Param.ValueArgsAsString ( "-NOTES"    , "");
       SAVEBOX   = Param.ValueArgsAsString("-SAVEBOX", "");
       PSAVEBOX  = Param.ValueArgsAsString("-PARAMBOX", "");
       OPTIMOD   = Param.ValueArgsAsString("-OPTIMOD","MAX");
       if (FIMGFA.isEmpty()){
          FIMGFA = IO.AddToPath(FWORK, FIMGFA);
       }        
        
       PlotFABest bestFA = new PlotFABest();
       String [] Notes = LABELS.split(",");
       LABELS = "";
       
       if(PBESTFA && FSTATE.equals("default")){
         LABELS = bestFA.getBestFA(IO.AddToPath(FWORK,"State.txt"),OPTIMOD,TOPGEN);
       }else if(PBESTFA){
         LABELS = bestFA.getBestFA(FSTATE,OPTIMOD,TOPGEN);  
       }
       
        for (String Note : Notes){ 
            if (Param.Exists(Note))
                LABELS += " " + Note + ":"+Param.ValueArgsAsString(Note, "");
        }
        
        
        
        }

    public void Run(){
        
     if(VIEWIFAZ){
        Plot2D Grafica = new Plot2D();
        JFrame frame   = new JFrame();
        frame.setTitle(HEAD);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         if(FSTATE.equals("default")){
        frame.add(Grafica.getContent(IO.AddToPath(FWORK,"State.txt"),LABELS,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,FIMGFA,TOPGEN,NOTES));
        frame.add(Grafica.getUIPanel(IO.AddToPath(FWORK,"State.txt"),frame,FIMGFA), "Last");
         }else{
        frame.add(Grafica.getContent(FSTATE,LABELS,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,FIMGFA,TOPGEN,NOTES));
        frame.add(Grafica.getUIPanel(FSTATE,frame,FIMGFA), "Last");
         }
        frame.setSize(1600,800);
        frame.setLocation(50,50);
        frame.setVisible(true);
        
        try   {
         BufferedImage     image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
          Graphics2D   graphics2D = image.createGraphics();
          frame.paint (graphics2D);
          ImageIO.write(image,"png", new File(FIMGFA));
         } catch(Exception e){}
        
     }else {
         
         Plot2D Grafica = new Plot2D();
         String rutaState = "";
         JPanel contenido;
        if(FSTATE.equals("default"))
            contenido=Grafica.getContent(IO.AddToPath(FWORK,"State.txt"),LABELS,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,FIMGFA,TOPGEN,NOTES);
         else
            contenido=Grafica.getContent(FSTATE,LABELS,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,FIMGFA,TOPGEN,NOTES);
         
         contenido.setSize(1600,800);
         contenido.setVisible(true);
         saveImage(contenido, FIMGFA);
         
         if(!SAVEBOX.equals("")){
             CONSOLE2 exe=new CONSOLE2(true);
             exe.Execute(this.SAVEBOX, " -CONFIG " + this.PSAVEBOX);
             
        
        }
  
    }
     
     
     
     
     

     
     
 }

    private void saveImage(JPanel panel,String ruta) {
    BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
    panel.paint(img.getGraphics());
    try {
        ImageIO.write(img, "png", new File(ruta));
        System.out.println("panel saved as image");

    } catch (Exception e) {
        System.out.println("panel not saved" + e.getMessage());
    }   
}
    
    
}
