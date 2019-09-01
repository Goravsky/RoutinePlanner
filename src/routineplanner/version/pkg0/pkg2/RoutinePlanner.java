/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routineplanner.version.pkg0.pkg2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class RoutinePlanner extends JFrame{
    
    private Calendar calendar;

    private DayPanel dayPanel;
    private ControlPanel controlPanel;
    private InfoPanel infoPanel;
    
    private int activeDay;
    int prevDay = 0;
    int currentYear;
    int currentMonth;
    int currentDay;
     
    RoutinePlanner(String s){
        super(s);
        
        this.setSize(850, 600);
        this.setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents(){
        calendar = new GregorianCalendar();
        currentYear = calendar.get(calendar.YEAR);
        currentMonth = calendar.get(calendar.MONTH);
        currentDay = calendar.get(calendar.DATE);
        
        calendar.set(currentYear, currentMonth, 1);
        
        dayPanel = new DayPanel(calendar);
        controlPanel = new ControlPanel(calendar);
        infoPanel = new InfoPanel();
        
        this.add(dayPanel,BorderLayout.CENTER);
        this.add(controlPanel,BorderLayout.PAGE_START);
        this.add(infoPanel,BorderLayout.PAGE_END);
        
        dayPanel.getDaysList().get(currentDay - 1).setCurrentDay();         //изменение цвета кнопки сегодняшнего дня
        dayPanel.parseNotes(currentYear, currentMonth + 1);    //проверка наличия записей на текущий месяц
        
        controlPanel.addListeners(nextButtonListener, prevButtonListener);
        infoPanel.addListener(addNoteListener);
        setDayButtonListener();
    }
    
    ActionListener nextButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            calendar.add(calendar.MONTH, 1);
            dayPanel.repaintMonthPanel(calendar);
            
            prevDay = 0;
            
            int newYear = calendar.get(calendar.YEAR);
            int newMonth = calendar.get(calendar.MONTH) + 1;
            
            controlPanel.setNewLabels(newYear,newMonth);
            if (newYear == currentYear && newMonth == currentMonth + 1){
                dayPanel.getDaysList().get(currentDay - 1).setCurrentDay(); 
            }
            
            infoPanel.setNote("");
            infoPanel.turnOffInfo();
            infoPanel.turnOffInfo();
            dayPanel.parseNotes(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH) + 1);
            
            setDayButtonListener();
        }
    };
    
    ActionListener prevButtonListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            calendar.add(calendar.MONTH, -1);
            dayPanel.repaintMonthPanel(calendar);
            
            prevDay = 0;
            
            int newYear = calendar.get(calendar.YEAR);
            int newMonth = calendar.get(calendar.MONTH) + 1;
            
            controlPanel.setNewLabels(newYear,newMonth);
            if (newYear == currentYear && newMonth == currentMonth + 1){
                dayPanel.getDaysList().get(currentDay - 1).setCurrentDay(); 
            }
            
            infoPanel.setNote("");
            infoPanel.turnOffInfo();
            infoPanel.turnOffInfo();
            dayPanel.parseNotes(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH) + 1);
            
            setDayButtonListener();
        }    
    };

    ActionListener dayButtonListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            activeDay = Integer.parseInt(ae.getActionCommand());
            
            dayPanel.getDaysList().get(activeDay - 1).turnOn();
            if (prevDay != 0 && prevDay != activeDay) {
                dayPanel.getDaysList().get(prevDay - 1).turnOff();
            }
            if(prevDay == 0){
                infoPanel.turnOnInfo();
            }
            infoPanel.setNote("");
            infoPanel.getFromXML(activeDay, controlPanel.getMonth(), controlPanel.getYear());

            prevDay = activeDay;  
        }       
    };
    
    ActionListener addNoteListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            infoPanel.addToXML(activeDay, controlPanel.getMonth(), controlPanel.getYear());
            if (infoPanel.getText().trim().equals("")){
                dayPanel.getDaysList().get(activeDay - 1).setNotNotedDay();
            }else{
                dayPanel.getDaysList().get(activeDay - 1).setNotedDay();
            }
        }        
    };
    
    public void setDayButtonListener(){
        for(int i = 0; i < dayPanel.getDaysList().size(); i++){
            dayPanel.getDaysList().get(i).addActionListener(dayButtonListener);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RoutinePlanner rp = new RoutinePlanner("Routine Planner");
        rp.setVisible(true);
        rp.setDefaultCloseOperation(rp.EXIT_ON_CLOSE);
    }
    
}
