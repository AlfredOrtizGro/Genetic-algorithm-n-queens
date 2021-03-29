/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import libs.ManejoArchivos;
import libs.ManejoER;

/**
 *
 * @author RenéArnulfo
 */
class PlotPanel extends JPanel {
    TSerie         SerieMax;
    TSerie         SerieAvr;
    TSerie         SerieMin;
    final int      PAD   = 50; //Margen
    final boolean  DEBUG = false;
    boolean        firstTime;  // Set at end of setData method
    Point2D.Double origin; //origen en los ejes
    Point2D.Double offset; //compensación
    double         xScale; //escala en eje x
    double         yScale; //escala en eje y
    float          xMax;
    float          xMin;
    float          yMax;
    float          yMin;
    //opciones de impresión
    String         LABELS; 
    String         NOTAS;
    boolean        PLOTMAX;
    String         PMAXTEXT; 
    boolean        PLOTPROM; 
    String         PPROMTEXT;
    boolean        PLOTMIN;
    String         PMINTEXT; 
    int            TOPGEN;
    ManejoArchivos IO = new ManejoArchivos();
 
    public PlotPanel(float[] x, float[] y,float[] x1, float[] y1) {
        setData(x, y, x1, y1);
    }
    
//    public PlotPanel(String Estado, String LABELS, boolean PLOTMAX,  String PMAXTEXT, 
//                                                 boolean PLOTPROM, String PPROMTEXT,
//                                                 boolean PLOTMIN,  String PMINTEXT, JFrame frame, String FIMGFA,int TOPGEN) {
     public PlotPanel(String Estado, String Labels, boolean PLOTMAX,  String PMAXTEXT, 
                                                 boolean PLOTPROM, String PPROMTEXT,
                                                 boolean PLOTMIN,  String PMINTEXT, String FIMGFA,int TOPGEN,String notas) {
        this.LABELS      = Labels;
        this.PLOTMAX   = PLOTMAX;
        this.PMAXTEXT  = PMAXTEXT;
        this.PLOTPROM  = PLOTPROM;
        this.PPROMTEXT = PPROMTEXT;
        this.PLOTMIN   = PLOTMIN;
        this.PMINTEXT  = PMINTEXT;
        this.TOPGEN    = TOPGEN;
        this.NOTAS     =notas;
        //setData (Estado,frame,FIMGFA);
        setData (Estado,FIMGFA);
    }
    
    public void GetExtremeValues (TSerie Serie){
      xMax = Math.max(xMax, Serie.xMax);
      xMin = Math.min(xMin, Serie.xMin);
      yMax = Math.max(yMax, Serie.yMax);
      yMin = Math.min(yMin, Serie.yMin);
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        
        if (PLOTMAX)  GetExtremeValues(SerieMax);
        if (PLOTPROM) GetExtremeValues(SerieAvr);
        if (PLOTMIN)  GetExtremeValues(SerieMin);
            
        xScale = (w - 2*PAD)/(xMax - xMin);
        yScale = (h - 2*PAD)/ (Math.abs(yMax)+ Math.abs(yMin));
        
        if(firstTime)
            System.out.printf("xScale = %.1f  yScale = %.1f%n", xScale, yScale);
        origin = new Point2D.Double(); // Axes origin.
        offset = new Point2D.Double(); // Locate data.
        if(xMax < 0 ) {//SerieMax.xMax < 0) {
            origin.x = w - PAD;
            offset.x = origin.x - xScale*xMax;//SerieMax.xMax;
        } else if( xMin < 0) { //SerieMax.xMin < 0) {
            origin.x = PAD - xScale * xMin;//xScale*SerieMax.xMin;
            offset.x = origin.x;
        } else {
            origin.x = PAD;
            offset.x = PAD - xScale* xMin;//SerieMax.xMin;
        }
        if( yMax < 0) { //SerieMax.yMax < 0) { //ajuste cuando todos los valores son negativos
            origin.y = PAD;
            offset.y = origin.y - yScale* yMax;//SerieMax.yMax;
        } else if( yMin < 0) {//SerieMax.yMin < 0) { // ya se sabe que yMax llega al cero o tiene una parte positiva, pero si tiene una parte negativa
            origin.y =  PAD - yScale * yMin;//SerieMax.yMin;
            offset.y = origin.y ;
        } else { // entonces todos los valores son positivos
            origin.y = h - PAD;
            offset.y = origin.y ;//PAD - yScale*Serie1.yMin; 
        }
        if(firstTime) {
            System.out.printf("origin = [%6.1f, %6.1f]%n", origin.x, origin.y);
            System.out.printf("offset = [%6.1f, %6.1f]%n", offset.x, offset.y);
        }
        
        
        
        
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, origin.y, w-PAD, origin.y));
        // Draw ordinate.
        g2.draw(new Line2D.Double(origin.x, PAD, origin.x, h-PAD));
        g2.setPaint(Color.YELLOW);
        // Mark origin.
        g2.fill(new Ellipse2D.Double(origin.x-2, origin.y-2, 4, 4));
 
        // Plot data
        if (PLOTMAX)  PlotData(g2,SerieMax);
        if (PLOTPROM) PlotData(g2,SerieAvr);
        if (PLOTMIN)  PlotData(g2,SerieMin);
         
        // Draw extreme data values.
        g2.setPaint(Color.black);
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm    = font.getLineMetrics("0", frc);
        String      s;
        float       width; 
        double      x;     
                
        //Etiqueta del eje X
        s = "Generaciones ";
        width  = (float)font.getStringBounds(s, frc).getWidth();
        float height = (float)font.getStringBounds(s, frc).getHeight();
        g2.setPaint(Color.ORANGE);
        g2.drawString(s, (float) (offset.x + xScale* xMax)-width, (float)origin.y+height+height);
        g2.setPaint(Color.RED);
        g2.drawString(s, (float) (offset.x + xScale* xMax)-width-1, (float)origin.y+height+height-1);
        g2.setPaint(Color.black);
               
        //Etiqueta del eje Y
        s = " FA ";
        width  = (float)font.getStringBounds(s, frc).getWidth();
        height = (float)font.getStringBounds(s, frc).getHeight();
        g2.setPaint(Color.ORANGE);
        g2.drawString(s, PAD/2, PAD/2);
        g2.setPaint(Color.RED);
        g2.drawString(s, PAD/2-1, PAD/2-1);
        g2.setPaint(Color.black);
        
        //TITULO
        Font Old = g2.getFont();
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));
        width  = (float)font.getStringBounds(LABELS, frc).getWidth();
        g2.setPaint(Color.GRAY);
        g2.drawString(LABELS, (float) w/2 - width, (float) PAD/2 );
        g2.drawString(NOTAS, (float) w/2 - width, (float) 100/2 );
        g2.setPaint(Color.BLACK);
        g2.setFont(Old);
        
        //periodo del eje x
        float Anchura = (float)font.getStringBounds(String.format("%.1f", xMax), frc).getWidth()+5; //anchura en pixeles de la letra
        float CadaX   = (float) xMax/Math.abs((float)((offset.x - xScale * xMax)-(offset.x - xScale * xMin))/Anchura);        
        for (float i = xMin; i <= xMax-CadaX; i += CadaX){
            s = String.format("%.1f", i);
            Anchura = (float)font.getStringBounds(s, frc).getWidth();
            g2.drawString(s, (float)(offset.x + xScale * i)-Anchura/2, (float)origin.y+lm.getAscent());
            }

        //fin del eje x
        s     = String.format("%.1f", xMax);
        width = (float)font.getStringBounds(s, frc).getWidth();
        x     = offset.x + xScale* xMax;
        g2.drawString(s, (float)x, (float)origin.y+lm.getAscent());

        //periodo del eje y
        float Altura  = (float)font.getStringBounds(String.format("%.1f", yMax), frc).getHeight()+1; //altura en pixeles de la letra
        float CadaY   = (float) yMax/Math.abs((float)((offset.y - yScale * yMax)-(offset.y - yScale * yMin))/Altura);
        width = (float)font.getStringBounds(String.format("%.1f", yMin), frc).getWidth();
        for (float i = yMin; i < yMax; i+=CadaY)
            g2.drawString(String.format("%.1f", i), (float)origin.x+1-width-(PAD/2), (float) (offset.y - yScale * i));
            
        //fin del eje Y
        s     = String.format("%.1f", yMax);
        double y     = offset.y - yScale* yMax;
        g2.drawString(s, (float)origin.x+1-width-(PAD/2), (float)y);
        
        if(firstTime)
            System.out.println("------------------------------");
        firstTime = false;
        
//        try {
//            
//         BufferedImage image      = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//         Graphics2D    graphics2D = image.createGraphics();
//         this.paint(graphics2D);
//         ImageIO.write(image,"jpeg", new File("jFrame.jpeg"));
//         }catch(Exception e){}
    }
    
    public void PlotData (Graphics2D g2, TSerie Serie){
        g2.setPaint(Serie.UsedColor);
        double Xant = 0d;
        double Yant = 0d;
        for(int i = 0; i < Serie.Valores.size(); i++) {
            double x1 = offset.x + xScale * Serie.Valores.get(i).x;
            double y1 = offset.y - yScale * Serie.Valores.get(i).y;
            if(firstTime)
                System.out.printf("i = %d  x1 = %6.1f  y1 = %.1f%n", i, x1, y1);
            g2.fill(new Ellipse2D.Double(x1-2, y1-2, 4, 4));
            String GEN = (Serie.OpPrint==1 || Serie.OpPrint==3 )? String.format("%.1f", Serie.Valores.get(i).x):"";
            GEN += (Serie.OpPrint==3)?" , ":"";
            String VAL = (Serie.OpPrint==2 || Serie.OpPrint==3 )? String.format("%.1f", Serie.Valores.get(i).y):"";
            g2.drawString(GEN+VAL, (float)x1+3, (float)y1-3);
            if (i>0)
              g2.draw( new Line2D.Double(Xant, Yant, x1, y1));
            
            Xant = x1;
            Yant = y1;
        }
    }

    
   // public void setData(String Estado, JFrame frame, String FIMGFA) {
    public void setData(String Estado,  String FIMGFA) {
        xMax = Float.MIN_VALUE;
        xMin = Float.MAX_VALUE;
        yMax = Float.MIN_VALUE;
        yMin = Float.MAX_VALUE;
        String [] Lineas    = IO.Read_Text_File(Estado);
        SerieMax            = new TSerie(Color.RED,PMAXTEXT);
        SerieAvr            = new TSerie(Color.BLUE,PPROMTEXT);
        SerieMin            = new TSerie(Color.MAGENTA,PMINTEXT);
        ManejoER Generacion = new ManejoER("====================Gene(.*)n:(\\d+)====================");
        ManejoER AdaptMax   = new ManejoER("Adapta(.*)n m(.*)xima:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        ManejoER AdaptAvr   = new ManejoER("Adaptaci(.*)n promedio:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        ManejoER AdaptMin   = new ManejoER("Adaptaci(.*)n m(.*)nima:(\\d+\\.?\\d+(E[-]*\\d+)*)");
        float g = 0f;
        float f = 0f;
        
        if(TOPGEN==0){
            for (int i = 0, Gen = 0; i < Lineas.length; i++) {
            if (Generacion.ExistER(Lineas[i]))                   
                g = Float.valueOf(Generacion.Grupo(2));
            else if (AdaptMax.ExistER(Lineas[i]) && PLOTMAX)                           
                SerieMax.Add(g,Float.valueOf(AdaptMax.Grupo(3)));
            else if (AdaptAvr.ExistER(Lineas[i]) && PLOTPROM){
                SerieAvr.Add(g,Float.valueOf(AdaptAvr.Grupo(2)));
            }else if (AdaptMin.ExistER(Lineas[i]) && PLOTMIN){
                SerieMin.Add(g,Float.valueOf(AdaptMin.Grupo(3)));
            }
            }//del for
        }else if(TOPGEN>0){
          for (int i = 0, Gen = 0; i < Lineas.length; i++) {
            if(Generacion.ExistER(Lineas[i]) &&  !Generacion.Grupo(1).equals(TOPGEN) ){
                if (Generacion.ExistER(Lineas[i]))
                    g = Float.valueOf(Generacion.Grupo(2));
                else if (AdaptMax.ExistER(Lineas[i]) && PLOTMAX)
                    SerieMax.Add(g,Float.valueOf(AdaptMax.Grupo(3)));
                else if (AdaptAvr.ExistER(Lineas[i]) && PLOTPROM)
                    SerieAvr.Add(g,Float.valueOf(AdaptAvr.Grupo(2)));
                else if (AdaptMin.ExistER(Lineas[i]) && PLOTMIN)
                    SerieMin.Add(g,Float.valueOf(AdaptMin.Grupo(3)));
            }
            }//del for
        }
        
        
        
        

        
        firstTime = DEBUG;
        repaint();
//        if (!FIMGFA.isEmpty()){
//          try   {
//             BufferedImage     image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
//             Graphics2D   graphics2D = image.createGraphics();
//             frame.paint (graphics2D);
//             ImageIO.write(image,"png", new File(FIMGFA));
//            } catch(Exception e){}
//          }
        
    }
    
    public void setData(float[] x, float[] y, float[] x1, float[] y1) {
        
        SerieMax    = new TSerie(x ,y ,Color.RED);
        SerieMin    = new TSerie(x1,y1,Color.MAGENTA);
        firstTime = DEBUG;
        repaint();
    }
 
    /**
     * Regresa los valores extremos de un arreglo de doubles, en la posicion [0] viene el valor mínimo y en [1] en valor máximo
     * @param Values arreglo con los valores a examinar 
     * @return arreglo con los valores mínimo y máximo de Values
     */
    private double[] getExtremeValues(double[] Values) {
        double min = Double.MAX_VALUE;
        double max = -min;
        for(int i = 0; i < Values.length; i++) {
            if(Values[i] < min) {
                min = Values[i];
            }
            if(Values[i] > max) {
                max = Values[i];
            }
        }
        return new double[] { min, max };
    }
}
