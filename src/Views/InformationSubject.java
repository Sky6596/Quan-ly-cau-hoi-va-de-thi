/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Object.Essay;
import Object.MultipleChoice;
import Object.Subject;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TungLe
 */
public class InformationSubject extends JFrame{
    
    private JButton btnOK;
    private Subject subject;
    private int numberOfChapters;
    
    public InformationSubject(Subject sj)
    {
        //super(sj.getNameSubject());
        this.setTitle(sj.getNameSubject());
        this.subject = sj;
        this.numberOfChapters = sj.getChapters().size();
        addControls();
        addEvents();
        showWindow();
    }
    
    public void addControls()
    {
        Container con = getContentPane();
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        //pnMain.setLayout(new GridLayout(subject.getChapters().size(), 0));
        JScrollPane scMain = new JScrollPane(
				pnMain,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        con.add(scMain);
        for(int i = 0; i < subject.getChapters().size(); i++)
        {
            JPanel pnSpace = new JPanel();
            pnSpace.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
  
            pnMain.add(pnSpace);
            JPanel pnChap = new JPanel();
            pnChap.setPreferredSize(new Dimension(pnMain.getPreferredSize().width - 30, 200));
            pnChap.setLayout(new GridLayout(1, 2, 10, 0));
            pnMain.add(pnChap);
            Border borderThongTin = BorderFactory.createLineBorder(Color.BLACK);
            String title = "Chương " + (i+1) + ": " + subject.getChapter(i).getNameChapter();
            TitledBorder borderTitleThongTin = new TitledBorder(borderThongTin, title);
            borderTitleThongTin.setTitlePosition(TitledBorder.TOP);
            pnChap.setBorder(borderTitleThongTin);

            JPanel pnMultipleChoice = new JPanel();
            pnChap.add(pnMultipleChoice);
            pnMultipleChoice.setLayout(new BoxLayout(pnMultipleChoice, BoxLayout.Y_AXIS));
            HashMap<Integer, Integer> levelAndNumber = new HashMap<Integer, Integer>();
            int sumMC = 0;
            for (MultipleChoice multipleChoice : subject.getMultipleChoices()) {
                if (subject.getChapter(i).getNameChapter().equals(multipleChoice.getChapter()) == false) continue;
                int level = (int) multipleChoice.getLevel();
                if(levelAndNumber.get(level) == null)
                    levelAndNumber.put(level, 1);
                else 
                {
                    int num = levelAndNumber.get(level) + 1;
                    levelAndNumber.remove(level);
                    levelAndNumber.put(level, num);
                }
                sumMC++;
            }
            Border borderMC = BorderFactory.createLineBorder(Color.RED);
            TitledBorder borderTitleMC = BorderFactory.createTitledBorder(borderMC, "Trắc nghiệm: " + sumMC);
            borderTitleMC.setTitlePosition(TitledBorder.TOP);
            borderTitleMC.setTitleColor(Color.RED);
            pnMultipleChoice.setBorder(borderTitleMC);
            
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("Mức độ");
            dtm.addColumn("Số câu");
            for (Map.Entry<Integer, Integer> e : levelAndNumber.entrySet()) {
                dtm.addRow(new Object[]{e.getKey(), e.getValue()}); 
            }
            JTable tblMC = new JTable(dtm);
            ((DefaultTableCellRenderer)tblMC.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            tblMC.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            tblMC.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            
            JScrollPane sc = new JScrollPane(
				tblMC,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pnMultipleChoice.add(sc);
            
            JPanel pnEssay = new JPanel();
            pnChap.add(pnEssay);
            pnEssay.setLayout(new BoxLayout(pnEssay, BoxLayout.Y_AXIS));
            levelAndNumber = new HashMap<Integer, Integer>();
            int sumES = 0;
            for (Essay essay : subject.getEssays()) {
                if (subject.getChapter(i).getNameChapter().equals(essay.getChapter()) == false) continue;
                int level = (int) essay.getLevel();
                if(levelAndNumber.get(level) == null)
                    levelAndNumber.put(level, 1);
                else 
                {
                    int num = levelAndNumber.get(level) + 1;
                    levelAndNumber.replace(level, num);
                }
                sumES++;
            }
            Border borderEssay = BorderFactory.createLineBorder(Color.RED);
            TitledBorder borderTitleEssay = BorderFactory.createTitledBorder(borderEssay, "Tự luận: " + sumES);
            borderTitleEssay.setTitlePosition(TitledBorder.TOP);
            borderTitleEssay.setTitleColor(Color.RED);
            pnEssay.setBorder(borderTitleEssay);
            
            dtm = new DefaultTableModel();
            dtm.addColumn("Mức độ");
            dtm.addColumn("Số câu");
            for (Map.Entry<Integer, Integer> e : levelAndNumber.entrySet()) {
                dtm.addRow(new Object[]{e.getKey(), e.getValue()}); 
            }
            JTable tblEssay = new JTable(dtm);
            ((DefaultTableCellRenderer)tblEssay.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            tblEssay.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            tblEssay.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            sc= new JScrollPane(
				tblEssay,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pnEssay.add(sc);
        }
        
        JPanel pnButton = new JPanel();
        pnButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnOK = new JButton("OK");
        pnButton.add(btnOK);
        pnMain.add(pnButton);
        
    }
    
    public void addEvents()
    {
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                processOut();
            }
        });
    }
    
    private void processOut()
    {
        this.dispose();
    }
    
    public void showWindow()
    {
        this.setSize(800, 700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
