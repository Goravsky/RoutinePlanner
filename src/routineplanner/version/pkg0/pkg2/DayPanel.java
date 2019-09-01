/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routineplanner.version.pkg0.pkg2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class DayPanel extends JPanel {

    private JPanel weekPanel = new JPanel();
    private JPanel monthPanel = new JPanel();

    private Calendar calendar;
    private ArrayList<DayButton> daysList;

    DayPanel(Calendar c) {
        super();
        calendar = c;
        daysList = new ArrayList<DayButton>();

        this.setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        
        weekPanel.setLayout(new GridLayout(1, 7, 1, 1));
        monthPanel.setLayout(new GridLayout(0, 7, 1, 1));
        

        weekPanel.add(new JButton("Понедельник"));
        weekPanel.add(new JButton("Вторник"));
        weekPanel.add(new JButton("Среда"));
        weekPanel.add(new JButton("Четверг"));
        weekPanel.add(new JButton("Пятница"));
        weekPanel.add(new JButton("Суббота"));
        weekPanel.add(new JButton("Воскресенье"));

        this.add(weekPanel, BorderLayout.PAGE_START);
        this.add(monthPanel, BorderLayout.CENTER);

        fillMonthPanel();
    }

    public void fillMonthPanel() {
        int firstDayOfMonth = getFirstWeekDay();

        for (int i = 0; i < firstDayOfMonth - 1; i++) {
            monthPanel.add(new JButton()).setEnabled(false);
        }

        for (int i = 0; i < calendar.getActualMaximum(calendar.DATE); i++) {
            daysList.add(new DayButton(Integer.toString(i + 1)));
        }

        for (int i = 0; i < calendar.getActualMaximum(calendar.DATE); i++) {
            monthPanel.add(daysList.get(i));
        }
    }

    public void repaintMonthPanel(Calendar c) {
        calendar = c;
        monthPanel.removeAll();
        daysList.clear();

        fillMonthPanel();

        this.repaint();
        monthPanel.repaint();
    }

    private int getFirstWeekDay() {
        int day = calendar.get(calendar.DAY_OF_WEEK);

        day--;
        if (day == 0) {
            day = 7;
        }
        return day;
    }

    public void addListeners(ActionListener al) {
        for (int i = 0; i < daysList.size(); i++) {
            daysList.get(i).addActionListener(al);
        }
    }

    public ArrayList<DayButton> getDaysList() {
        return daysList;
    }

    public void parseNotes(int year, int month){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("Notes.xml");
            d.getDocumentElement().normalize();

            NodeList notes = d.getElementsByTagName("Note");

            for (int i = 0; i < notes.getLength(); i++) {
                Node xmlNote = notes.item(i);
                Element noteElement = (Element) xmlNote;

                NodeList dayNodes = noteElement.getElementsByTagName("day");
                NodeList monthNodes = noteElement.getElementsByTagName("month");
                NodeList yearNodes = noteElement.getElementsByTagName("year");

                Node dayNode = dayNodes.item(0);
                Node monthNode = monthNodes.item(0);
                Node yearNode = yearNodes.item(0);
                
                if (Integer.parseInt(monthNode.getTextContent()) == month
                        && Integer.parseInt(yearNode.getTextContent()) == year) {
                        
                    for (int j = 0; j < daysList.size(); j++) {
                        if (dayNode.getTextContent().equals(daysList.get(j).getText())) {
                            daysList.get(j).setNotedDay();
                        }
                    }
                }
            }
        } catch (SAXException ex) {
            Logger.getLogger(DayPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DayPanel.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ParserConfigurationException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
