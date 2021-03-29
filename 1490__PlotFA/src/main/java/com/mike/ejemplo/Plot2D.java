/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.ejemplo;

/**
 *
 * @author RenéArnulfo
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
 
public class Plot2D {
    PlotPanel  plotPanel;

     /**
     * Obtiene SIZE puntos X,Y aleatorios los cuales son dibujados y regresados en un JPanel
     * @return Panel con 
     */
    public JPanel getContent(String Estado, String NOTE, boolean PLOTMAX,  String PMAXTEXT, 
                                                         boolean PLOTPROM, String PPROMTEXT,
                                                         boolean PLOTMIN,  String PMINTEXT,String FIMGFA,int TOPGEN,String Notas) {
//      public JPanel getContent(String Estado, String NOTE, boolean PLOTMAX,  String PMAXTEXT, 
//                                                         boolean PLOTPROM, String PPROMTEXT,
//                                                         boolean PLOTMIN,  String PMINTEXT, JFrame frame,String FIMGFA,int TOPGEN) {
        //plotPanel = new PlotPanel(Estado,NOTE,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,frame,FIMGFA,TOPGEN);
         plotPanel = new PlotPanel(Estado,NOTE,PLOTMAX,PMAXTEXT,PLOTPROM,PPROMTEXT,PLOTMIN,PMINTEXT,FIMGFA,TOPGEN,Notas);
        return plotPanel;
    }
    
    public JPanel getUIPanel(String Archivo, JFrame frame, String FIMGFA) {
        JButton button          = new JButton("UpDate");
        JButton buttonImg       = new JButton("SaveImg");
                
        JRadioButton[] rbs      = new JRadioButton[3];
        final ButtonGroup group = new ButtonGroup();
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //int index = Integer.parseInt( group.getSelection().getActionCommand());
                //plotPanel.setData(Archivo, frame, FIMGFA);
                 plotPanel.setData(Archivo,  FIMGFA);
                }
            });
        
        buttonImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
            BufferedImage     image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
             Graphics2D   graphics2D = image.createGraphics();
             frame.paint (graphics2D);
                try {
                    ImageIO.write(image,"png", new File(FIMGFA));
                } catch (IOException ex) {
                    Logger.getLogger(Plot2D.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JPanel             panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc   = new GridBagConstraints();
        String             minus = "<html>\u2013";
        String[] ids = { "<html>Adaptación Máxima", "Adaptación Promedio", "<html>Adaptación Mínima" };
        
        rbs[0] = new JRadioButton(ids[0], 0 == 2);
        rbs[0].setBackground(Color.decode("0xFF5522"));
        group.add(rbs[0]);
        panel.add(rbs[0], gbc);
        rbs[1] = new JRadioButton(ids[1], 0 == 2);
        rbs[1].setBackground(Color.decode("0x2255FF"));
        group.add(rbs[1]);
        panel.add(rbs[1], gbc);
        rbs[2] = new JRadioButton(ids[2], 0 == 2);
        rbs[2].setBackground(Color.magenta);
        group.add(rbs[2]);
        panel.add(rbs[2], gbc);
        
                        
        panel.setBorder(BorderFactory.createEtchedBorder());
        gbc.weightx = 1.0;
        panel.add(button, gbc);
        panel.add(buttonImg,gbc);
        return panel;
    }

    
 
}
 
