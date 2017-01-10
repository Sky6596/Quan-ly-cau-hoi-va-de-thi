/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.ExamModel;
import Models.SubjectsModel;
import Object.Exams;
import Object.Subject;
import Object.Subjects;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;



/**
 *
 * @author TungLe
 */
public class FunctionViewing extends JDialog{
    
    private JPanel pnMain;
    private JPanel pnSubject;
    private JPanel pnButton;
    private JComboBox<String> comboFunctions;
    private JComboBox<String> comboSubjects;
    private String[] vtNameFunction = { "...",
                                        "Thông tin tổng thể",
                                        "Thống kê các đề thi",
                                        "Thống kê theo từng môn học"};   
    public static Subjects subjects;
    private JButton btnOK;
    private JButton btnCancel;
    
    public FunctionViewing()
    {
        this.setTitle("Thông kê dữ liệu");
        comboFunctions = new JComboBox<String>(vtNameFunction);
        showFunctions();
        addEvents();
    }
    
    public void showFunctions()
    {
        Container con = getContentPane();
        pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        con.add(pnMain);
        
        JPanel pnFunctions = new JPanel();
        pnFunctions.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        JLabel lblChooseMode = new JLabel("Thống kê: ");
        pnFunctions.add(lblChooseMode);
        pnFunctions.add(comboFunctions);
        pnMain.add(pnFunctions);
        
    }
    public void addEvents()
    {
        comboFunctions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboFunctions.getSelectedIndex() != - 1) 
                {
                    int k = comboFunctions.getSelectedIndex();
                    clearSubjectsButtons();
                    switch(k)
                    {
                        case 0:
                            break;
                        case 1:
                            showOverallInformation();
                            break;
                        case 2:
                            showButtons();
                            pnMain.updateUI();
                            break;
                        case 3:
                            showEachSubject();
                            pnMain.updateUI();
                            break;
                    }
                }
            }
        });
    }
    
    private void showOverallInformation()
    {
        String strResult = "";
        subjects = SubjectsModel.readSubjects();
        int numberOfSubjects = subjects.getSubjects().size();
        strResult += "Số lượng cấc môn học: " + numberOfSubjects + "\n";
        Exams exams = ExamModel.readExam();
        int numberOfExams = exams.getExams().size();
        strResult += "Số lượng các đề thi: " + numberOfExams + "\n";
        
        int sumOfMultipleChoices = 0;
        int sumOfEssays = 0;
        
        for(int i = 0; i < numberOfSubjects; i++)
        {
            Subject sj = subjects.getSubject(i);
            sumOfMultipleChoices += sj.getMultipleChoices().size();
            sumOfEssays += sj.getEssays().size();
        }
        
        strResult += "Tổng số câu trắc nghiệm: " + sumOfMultipleChoices + "\n";
        strResult += "Tổng số câu tự luận: " + sumOfEssays + "\n";
        
        JOptionPane.showMessageDialog(null, strResult);
    }
    
    private void showEachSubject()
    {
        pnSubject = new JPanel();
        pnSubject.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
        subjects = SubjectsModel.readSubjects();
        int numberOfSubjects = subjects.getSubjects().size();
        String[] strSubjects = new String[numberOfSubjects+1];
        strSubjects[0] = "...";
        for(int i = 1; i <= numberOfSubjects; i++)
        {
            strSubjects[i] = subjects.getSubject(i-1).getNameSubject();
        }
        comboSubjects = new JComboBox<String>(strSubjects);
        JLabel lblSubject = new JLabel("Môn học: ");
        JLabel lblChooseMode = new JLabel("Thống kê: ");
        lblSubject.setPreferredSize(lblChooseMode.getPreferredSize());
        pnSubject.add(lblSubject);
        pnSubject.add(comboSubjects);
        pnMain.add(pnSubject);
        showButtons();
    }
    
    private void showButtons()
    {
        pnButton = new JPanel();
        pnButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");
        pnButton.add(btnOK);
        pnButton.add(btnCancel);
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboFunctions.getSelectedIndex() == 3
                        && comboSubjects.getSelectedIndex() > 0)
                {
                    InformationSubject is = new InformationSubject(subjects.getSubject(
                                        comboSubjects.getSelectedIndex() - 1));
                }
                if(comboFunctions.getSelectedIndex() == 2)
                {
                    if(subjects == null) subjects = SubjectsModel.readSubjects();
                    InformationExams ies = new InformationExams();
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                processOut();
            }
        });
        pnMain.add(pnButton);
    }
    
    private void processOut()
    {
        this.dispose();
    }
    
    public void clearSubjectsButtons()
    {
        if(pnSubject != null)
        {
            pnMain.remove(pnSubject); 
        }
        if(pnButton != null)
        {
             pnMain.remove(pnButton);
        }
        pnMain.updateUI();
    }
    
    public void showWindow()
    {
        this.setSize(400, 200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
