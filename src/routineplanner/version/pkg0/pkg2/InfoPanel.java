/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routineplanner.version.pkg0.pkg2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class InfoPanel extends JPanel {

    private JTextArea noteField;
    private JButton addNoteButton;
    private File fileXML;

    InfoPanel() {
        super();
        
        noteField = new JTextArea("Ваша заметка");
        //Color cl2 = new Color(202, 228, 255);
        addNoteButton = new JButton();
        //addNoteButton.setBackground(cl2);
        fileXML = new File("Notes.xml");
        
        this.setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents(){
        Font noteFont = new Font("TimesRoman", Font.ROMAN_BASELINE, 16);     
        noteField.setFont(noteFont);
        
        noteField.setEnabled(false);
        addNoteButton.setEnabled(false);
        
        ImageIcon iconNext = new ImageIcon("images\\add.png");
        addNoteButton.setIcon(iconNext);
        
        this.add(noteField, BorderLayout.CENTER);
        this.add(addNoteButton, BorderLayout.EAST);
    }
    
    public String getText(){
        return noteField.getText();
    }
    
    public void setNote(String s){
        noteField.setText(s);
    }
    
    public void addListener(ActionListener al){
        addNoteButton.addActionListener(al);
    }
    
    public void turnOnInfo() {
        noteField.setEnabled(true);
        addNoteButton.setEnabled(true);
    }
    
    public void turnOffInfo(){
        noteField.setEnabled(false);
        addNoteButton.setEnabled(false);
    }
    
    public void getFromXML(int day, int month, int year){
        try { 
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(fileXML);
            d.getDocumentElement().normalize();
            
            NodeList notes = d.getElementsByTagName("Note");
            
            for (int i = 0; i < notes.getLength(); i++){
                Node xmlNote = notes.item(i);
                Element noteElement = (Element) xmlNote;
                
                NodeList dayNodes = noteElement.getElementsByTagName("day");
                NodeList monthNodes = noteElement.getElementsByTagName("month");
                NodeList yearNodes = noteElement.getElementsByTagName("year");
                NodeList contentNodes = noteElement.getElementsByTagName("content");
                
                Node dayNode = dayNodes.item(0);
                Node monthNode = monthNodes.item(0);
                Node yearNode = yearNodes.item(0);
                Node contentNode = contentNodes.item(0);
                
                if (Integer.parseInt(dayNode.getTextContent()) == day
                     && Integer.parseInt(monthNode.getTextContent()) == month
                     && Integer.parseInt(yearNode.getTextContent()) == year) {
                        noteField.setText(contentNode.getTextContent());
                }
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addToXML(int day, int month, int year){
        try {
            //File fileXML = new File("Notes.xml"); 
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(fileXML);
            d.getDocumentElement().normalize();
            Element root = d.getDocumentElement();

            NodeList notes = root.getElementsByTagName("Note");

            boolean noteFoundFlag = false;
            for (int i = 0; i < notes.getLength(); i++){
                Node xmlNote = notes.item(i);
                Element noteElement = (Element) xmlNote;
                
                NodeList dayNodes = noteElement.getElementsByTagName("day");
                NodeList monthNodes = noteElement.getElementsByTagName("month");
                NodeList yearNodes = noteElement.getElementsByTagName("year");
                NodeList contentNodes = noteElement.getElementsByTagName("content");
                
                Node dayNode = dayNodes.item(0);
                Node monthNode = monthNodes.item(0);
                Node yearNode = yearNodes.item(0);
                Node contentNode = contentNodes.item(0);
                
                if (Integer.parseInt(dayNode.getTextContent()) == day
                        && Integer.parseInt(monthNode.getTextContent()) == month
                        && Integer.parseInt(yearNode.getTextContent()) == year) {
                    noteFoundFlag = true;

                    if (noteField.getText().trim().equals("")) {
                        System.out.println("Пустая строка");
                        xmlNote.removeChild(dayNode);
                        xmlNote.removeChild(monthNode);
                        xmlNote.removeChild(yearNode);
                        xmlNote.removeChild(contentNode);
                        root.removeChild(xmlNote);
                        noteField.setText("");
                    } else {
                        contentNode.setTextContent(noteField.getText().trim());
                        System.out.println(contentNode.getTextContent());
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(d);
                    StreamResult result = new StreamResult(fileXML);
                    transformer.transform(source, result);
                }
            }

            if (!noteFoundFlag && !noteField.getText().trim().equals("")) {
                Element newNoteNode = d.createElement("Note");
                root.appendChild(newNoteNode);
                
                Element newDayNode = d.createElement("day");
                Element newMonthNode = d.createElement("month");
                Element newYearNode = d.createElement("year");
                Element newContentNode = d.createElement("content");
                
                newNoteNode.appendChild(newDayNode);
                newNoteNode.appendChild(newMonthNode);
                newNoteNode.appendChild(newYearNode);
                newNoteNode.appendChild(newContentNode);
                
                newDayNode.setTextContent(Integer.toString(day));
                newMonthNode.setTextContent(Integer.toString(month));
                newYearNode.setTextContent(Integer.toString(year));
                newContentNode.setTextContent(noteField.getText().trim());

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(d);
                StreamResult result = new StreamResult(fileXML);
                transformer.transform(source, result);  
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DayButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
