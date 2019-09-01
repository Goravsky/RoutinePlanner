/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routineplanner.version.pkg0.pkg2;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author User
 */
public class DayButton extends JButton {
    
    Border onBorder = BorderFactory.createLineBorder(Color.RED);
    Border regularBorder = BorderFactory.createLineBorder(Color.lightGray);
    ImageIcon noteIcon = new ImageIcon("images\\note.png");
    JLabel labelNote = new JLabel();
    
    DayButton(String s){
        super(s);
        
        this.setBorder(regularBorder);
       
        this.setBackground(null);
    }
    
    public void turnOn() {
        Color cl1 = new Color(187, 255, 255);
        this.setBackground(cl1);
    } 
    
    public void turnOff(){
        this.setBackground(null);
        
    }
    
    public void setCurrentDay(){
        this.setBorder(onBorder);
    }
    
    public void setNotedDay(){
        labelNote.setIcon(noteIcon);
        this.add(labelNote, BorderLayout.PAGE_START);
    }
    
    public void setNotNotedDay(){
        labelNote.setVisible(false);
        
    }
}
