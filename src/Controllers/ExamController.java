/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ExamModel;
import Models.SubjectsModel;
import Object.Answer;
import Object.Essay;
import Object.Exam;
import Object.Exams;
import Object.MultipleChoice;
import Object.Question;
import Object.Subjects;
import Views.DoingExam;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author TungLe
 */
public class ExamController {
    public static DoingExam doingExam;
    public static Exam exam;
    public static Exams exams;
    private static DefaultTableModel model;
    public static String CreateExamForm;
    
    public static void loadTableExam(JTable table) {
        exams = ExamModel.readExam();
        
        model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for(int i = 0; i < exams.getExams().size(); ++i) {
            Exam exam = exams.getExam(i);
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
            model.addRow(new Object[]{i + 1, exam.getNameExam(), timeFormat.format(exam.getCreateday())});      
        }
    }
    
    public static void showExam(JTable tableExam, JPanel exam) 
    {
        exam.removeAll();
        model = (DefaultTableModel) tableExam.getModel();
        int selectRow = tableExam.getSelectedRow();
        exam.setLayout(new BorderLayout());
        if (selectRow != -1) {
            int idExam = (int)model.getValueAt(selectRow, 0) - 1;
            exams = ExamModel.readExam();
            Exam ex = exams.getExam(idExam);
            JPanel pnExam = new JPanel();
            pnExam.setLayout(new BoxLayout(pnExam, BoxLayout.Y_AXIS));
            JPanel pnName = new JPanel();
            pnName.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lblName = new JLabel("<html><font size = '20'><b>" + ex.getNameExam() + "</b></font></html>");
            pnName.add(lblName);
            pnName.setPreferredSize(new Dimension(lblName.getPreferredSize().width, lblName.getPreferredSize().height + 20));
            pnExam.add(pnName);
            
            Font fontPart = new Font("arial", Font.BOLD, 15);
            
            ArrayList<Question> question = ex.getQuestions();
            
            boolean haveMultipleChoice = false; 
            boolean haveEssay = false;          
            // Kiểm tra xem có phần tự luận không
            for (Question q : question)
            {
                if(q instanceof Essay)
                {
                    haveEssay = true;
                }
                // Kiểm tra xem có phần trắc nghiệm không}
                else 
                {
                    haveMultipleChoice = true;
                }
                if (haveEssay && haveMultipleChoice) break;
            }
            
            if(haveEssay)
            {
                JPanel pnPart = new JPanel();
                pnPart.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel lblEs = new JLabel("Tự luận");
                lblEs.setFont(fontPart);
                pnPart.add(lblEs);
                pnExam.add(pnPart);
            }
            
            int count = 0;
            for (Question q : question)
            {
                if (q instanceof Essay)
                {
                      count++;
                      JPanel pnEssay = new JPanel();
                      String content = "Câu " + count + ": " + q.printfQuestion();
                      JLabel jlabelContent = new JLabel("<html><body style='width: " + exam.getPreferredSize().width + "'>" + content + "</body></html>", SwingConstants.LEFT);
                      pnEssay.setLayout(new FlowLayout(FlowLayout.LEFT));
                      pnEssay.add(jlabelContent); 
                      pnExam.add(pnEssay);
                }
            }

            if(haveMultipleChoice)
            {
                JPanel pnPart = new JPanel();
                pnPart.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel lblMC = new JLabel("Trắc nghiệm");
                lblMC.setFont(fontPart);
                pnPart.add(lblMC);
                pnExam.add(pnPart);
            }
           
            count = 0;
            for (Question q : question)
            {
                if (q instanceof MultipleChoice)
                {
                      count++;
                      JPanel pnMultipleChoice = new JPanel();
                      String content = "Câu " + count + ": " + q.printfQuestion();
                      JLabel jlabelContent = new JLabel("<html><body style='width: " + exam.getPreferredSize().width + "'>" + content + "</body></html>", SwingConstants.LEFT);
                      pnMultipleChoice.setLayout(new FlowLayout(FlowLayout.LEFT));
                      pnMultipleChoice.add(jlabelContent); 
                      pnExam.add(pnMultipleChoice);
                }
            }
            JScrollPane sc = new JScrollPane(pnExam, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            exam.add(sc, BorderLayout.CENTER);
            exam.updateUI();
        }
    }
    
    public static void deleteExam(JTable tableExam, JPanel exam) {
        model = (DefaultTableModel) tableExam.getModel();
        int selectRow = tableExam.getSelectedRow();
        
        if (selectRow != -1) {
            int idExam = (int)model.getValueAt(selectRow, 0);
            String ex = "Bạn muốn xóa đề thi có id : " + idExam;
            if (JOptionPane.showConfirmDialog(null, ex, "Thông báo", 2) == 0) {
                exams = ExamModel.readExam();
                exams.removeExam(idExam-1);
                ExamModel.writeExam(exams);
                model.removeRow(selectRow);
                loadTableExam(tableExam);
                exam.removeAll();
                exam.updateUI();
            }
            
        }
    }
    public static ComboBoxModel getNameSubjects() {
        Subjects subjects = SubjectsModel.readSubjects();
        int size = subjects.getSubjects().size();
        String[] items = new String[size + 1];
        items[0] = "Tất cả";
        for (int i = 1; i <= size; i++) {
            items[i] = subjects.getSubject(i-1).getNameSubject();
        }
        return new DefaultComboBoxModel(items);

    }

    public static void loadTableExam(JTable tableExam, Object selectedItem) {
        int rowCount = model.getRowCount();
        String mode = selectedItem.toString();
        if(mode.equals("Tất cả") == false) {
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            for(int i = 0; i < exams.getExams().size(); ++i) {
                Exam exam = exams.getExam(i);
                if(exam.getNameSubject().equals(mode)) {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
                    model.addRow(new Object[]{i + 1, exam.getNameExam(), timeFormat.format(exam.getCreateday())});   
                }
            }
        }
        else loadTableExam(tableExam);
        tableExam.updateUI();
    }
    
    public static void viewExam() {
        Dimension size = doingExam.content.getSize();
        MultipleChoice mul;
        Essay essay;
        doingExam.content.setLayout(new FlowLayout());
        for(int i = 0; i < exam.getQuestions().size(); ++i) {
            
            JPanel jPanelQuestion = new JPanel();
            GroupLayout layout = new GroupLayout(jPanelQuestion);
            jPanelQuestion.setLayout(layout);
            
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            GroupLayout.ParallelGroup horizontalGroupQuestions = layout.createParallelGroup(LEADING);
            GroupLayout.SequentialGroup verticalGroupQuestions = layout.createSequentialGroup();
            
            if(exam.getQuestion(i) instanceof MultipleChoice) {
                mul = (MultipleChoice) exam.getQuestion(i);
                StringBuilder contentQuestion = new StringBuilder();
                contentQuestion.append("Câu ").append(i + 1).append(": ");
                contentQuestion.append(mul.getContentQuestion());
                JLabel jlabelContent = new JLabel("<html><body style='width: " + size.width + "'>" + contentQuestion.toString() + "</body></html>", SwingConstants.LEFT);
                
                GroupLayout.SequentialGroup e = layout.createSequentialGroup();
                horizontalGroupQuestions.addComponent(jlabelContent);
                for (int j = 0; j < mul.getAnswers().size(); ++j) {
                    JCheckBox TFAns = new JCheckBox();
                    TFAns.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    TFAns.setText("<html><body style='width: " + (size.width - 40) + "'>" + mul.getAnswer(i).getContentAnswer() + "</body></html>");
                    horizontalGroupQuestions.addComponent(TFAns);
                    e.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(TFAns)));
                }
                
                verticalGroupQuestions.addComponent(jlabelContent);
                verticalGroupQuestions.addGroup(layout.createParallelGroup(LEADING)
                        .addGroup(e));
                layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(horizontalGroupQuestions));
                
                layout.setVerticalGroup(verticalGroupQuestions);
                doingExam.content.setPreferredSize(new Dimension(size.width, doingExam.content.getPreferredSize().height + jPanelQuestion.getPreferredSize().height + 5));
                doingExam.content.add(jPanelQuestion);
            } else {
                essay = (Essay) exam.getQuestion(i);
                StringBuilder contentQuestion = new StringBuilder();
                contentQuestion.append("Câu ").append(i + 1).append(": ");
                contentQuestion.append(essay.getContentQuestion());
                JLabel jlabelContent = new JLabel("<html><body style='width: " + size.width + "'>" + contentQuestion.toString() + "<br>Gợi ý: " + essay.getSuggest() +"</body></html>", SwingConstants.LEFT);

                GroupLayout.SequentialGroup e = layout.createSequentialGroup();
                horizontalGroupQuestions.addComponent(jlabelContent);
                
                JTextArea task = new JTextArea("Điền bài làm vào đây",10,50);
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                task.setBorder(BorderFactory.createCompoundBorder(border,
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                JScrollPane scrollPane = new JScrollPane(task);
                horizontalGroupQuestions.addComponent(scrollPane);
                e.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(scrollPane)));
                

                verticalGroupQuestions.addComponent(jlabelContent);
                verticalGroupQuestions.addGroup(layout.createParallelGroup(LEADING)
                        .addGroup(e));
                layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(horizontalGroupQuestions));

                layout.setVerticalGroup(verticalGroupQuestions);
                doingExam.content.setPreferredSize(new Dimension(size.width, doingExam.content.getPreferredSize().height + jPanelQuestion.getPreferredSize().height + 5));
                doingExam.content.add(jPanelQuestion);
            }
        }
        doingExam.content.updateUI();
    }

    public static void doingExam(JTable tableExam) {
        model = (DefaultTableModel) tableExam.getModel();
        int selectRow = tableExam.getSelectedRow();

        if (selectRow != -1) {
            int STT = (int) model.getValueAt(selectRow, 0);
            exam = exams.getExam(STT - 1);
            doingExam = new DoingExam(null, true);
            viewExam();
            doingExam.setVisible(true);
        }
    }

    public static void submitExam() {
        
        String confirm = "Bạn chắc chắn muốn nộp bài";
        if (JOptionPane.showConfirmDialog(null, confirm, "Thông báo", 2) == 0) {
            int soCauDung = 0;
            int soCauTN = 0;
            for (int i = 0; i < exam.getQuestions().size(); ++i) {
                if(exam.getQuestion(i) instanceof MultipleChoice) {
                    soCauTN++;
                    MultipleChoice mul = (MultipleChoice) exam.getQuestion(i);
                    JPanel jPanelquestion = (JPanel) doingExam.content.getComponent(i);
                    Question question = exam.getQuestion(i);
                    soCauDung++;
                    for (int j = 1; j < jPanelquestion.getComponentCount(); ++j) {
                        JCheckBox ans = (JCheckBox) jPanelquestion.getComponent(j);
                        if (mul.getAnswer(j - 1).getAccuracy()) {
                            ans.setText("<html><font color='red'>" + ans.getText() + "</font></html>");
                        }
                        if (ans.isSelected() != mul.getAnswer(j - 1).getAccuracy()) {
                            soCauDung--;
                            break;
                        }
                    }
                    for (int j = 1; j < jPanelquestion.getComponentCount(); ++j) {
                        JCheckBox ans = (JCheckBox) jPanelquestion.getComponent(j);
                        if (mul.getAnswer(j - 1).getAccuracy()) {
                            ans.setText("<html><font color='red'>" + ans.getText() + "</font></html>");
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Bạn làm đúng " + soCauDung + " câu hỏi trắc nghiệm trong số " + soCauTN, "Thông báo", 3);

        }
    }
    
}