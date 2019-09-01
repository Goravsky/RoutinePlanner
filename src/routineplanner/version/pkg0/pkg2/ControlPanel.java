/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routineplanner.version.pkg0.pkg2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class ControlPanel extends JPanel{
    
    private JButton nextButton = new JButton();
    private JButton prevButton = new JButton();
    
    private JLabel monthLabel = new JLabel();
    private JLabel yearLabel = new JLabel();
    
    private Calendar calendar;

   
    ControlPanel(Calendar c){
        super();
       
        calendar = c;
        this.setLayout(new BorderLayout());
        InitComponents();
    }
    
    private void InitComponents(){
        Font yearFont = new Font("TimesRoman", Font.BOLD, 30);           // шрифт лейбла года
        yearLabel.setText(Integer.toString(calendar.get(calendar.YEAR)));
        yearLabel.setFont(yearFont);
        
        Font monthFont = new Font("TimesRoman", Font.BOLD, 27);          //шрифт лейбла месяца
        monthLabel.setText(monthName(calendar.get(calendar.MONTH) + 1));
        monthLabel.setFont(monthFont);
        
        ImageIcon iconNext = new ImageIcon("images\\next.png");
        ImageIcon iconPrev = new ImageIcon("images\\prev.png");
        
       // Color cl2 = new Color(202, 228, 255);
        nextButton.setIcon(iconNext);
        //nextButton.setBackground(cl2);
        prevButton.setIcon(iconPrev);
        //prevButton.setBackground(cl2);
     
        JPanel monthControlPanel = new JPanel();
       
        monthControlPanel.add(prevButton);
        monthControlPanel.add(monthLabel);
        monthControlPanel.add(nextButton);
        
        this.add(yearLabel, BorderLayout.WEST);
        this.add(monthControlPanel,BorderLayout.CENTER);
    }
    
    public String monthName(int month) {
        switch (month) {
            case 1:
                return " Январь ";
            case 2:
                return "Февраль ";
            case 3:
                return "   Март   ";
            case 4:
                return " Апрель ";
            case 5:
                return "   Май    ";
            case 6:
                return "   Июнь   ";
            case 7:
                return "   Июль   ";
            case 8:
                return " Август ";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь ";
            case 11:
                return " Ноябрь ";
            case 12:
                return " Декабрь ";
            default:
                return "ОШИБКА";
        }

    }
    
    public void setNewLabels(int y, int m){
        yearLabel.setText(Integer.toString(y));
        monthLabel.setText(monthName(m));
    }
    
    public void addListeners(ActionListener al1, ActionListener al2){
        nextButton.addActionListener(al1);
        prevButton.addActionListener(al2);
    }
    
    public int getMonth(){
        return calendar.get(calendar.MONTH) + 1;
    }
    
    public int getYear(){
        return calendar.get(calendar.YEAR);
    }
}
