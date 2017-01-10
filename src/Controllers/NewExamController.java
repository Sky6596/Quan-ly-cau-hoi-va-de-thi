/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.ExamController.CreateExamForm;
import static Controllers.SubjectController.subjects;
import Models.ExamModel;
import Models.SubjectsModel;
import Object.Exam;
import Object.Exams;
import Object.Question;
import Views.RandomExam;
import Views.SelectedExam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 *
 * @author 8TITTIT8
 */
public class NewExamController {
    public static RandomExam newDialogRandomExam = new RandomExam(null, true);
    public static SelectedExam newDialogSelectedExam = new SelectedExam(null, true);
    public static Exam selectedExam;
    public static ArrayList<Question> questions;
    
    public static void loadSubjectCreate(JComboBox subjects){
        subjects.removeAllItems();
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().size(); ++i) {
            subjects.addItem(SubjectController.subjects.getSubjects().get(i).getNameSubject());
        }
    }
    
    public static void loadLevelRandomCreate(int index){
        newDialogRandomExam.level1.removeAllItems();
        newDialogRandomExam.level2.removeAllItems();
        boolean[] checkEssayLevels = {false, false, false, false, false, false};
        boolean[] checkMultipleChoiceLevels = {false, false, false, false, false, false};
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().get(index).getMultipleChoices().size(); ++i){
            checkMultipleChoiceLevels[intValue(SubjectController.subjects.getSubjects().get(index).getMultipleChoices().get(i).getLevel())] = true;
        }
        for(int i = 0; i <= 5; ++i)
            if (checkMultipleChoiceLevels[i] == true){
                newDialogRandomExam.level1.addItem(Integer.toString(i));
            }
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().get(index).getEssays().size(); ++i){
            checkEssayLevels[intValue(SubjectController.subjects.getSubjects().get(index).getEssays().get(i).getLevel())] = true;
        }
        for(int i = 0; i <= 5; ++i) {
            if (checkEssayLevels[i] == true) {
                newDialogRandomExam.level2.addItem(Integer.toString(i));
            }
        }
    }
    
    public static void loadChapterRandomCreate(int index){
        newDialogRandomExam.chapters.removeAllItems();
        DefaultTableModel tableModel = new DefaultTableModel();
        
        String[] colsName = {"Chương", "Dạng câu hỏi", "Độ khó", "Số lượng"};
        tableModel.setColumnIdentifiers(colsName);
        
        newDialogRandomExam.nameExam.setText("");
        newDialogRandomExam.time.setText("");
        newDialogRandomExam.numberMultipleChoice.setText("");
        newDialogRandomExam.numberEssay.setText("");
        newDialogRandomExam.table.setModel(tableModel);
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().get(index).getChapters().size(); ++i) {
            newDialogRandomExam.chapters.addItem(SubjectController.subjects.getSubjects().get(index).getChapters().get(i).getNameChapter());
        }
        loadLevelRandomCreate(index);
    }
    
    public static void viewNewExam(){
        subjects = SubjectsModel.readSubjects();
        
        String[] pizzas = {"Tạo đề tự động", "Tạo đề bằng tay"};
        JFrame frame = new JFrame("create exam form");
        CreateExamForm = (String) JOptionPane.showInputDialog(frame,
                "Hình thức tạo đề thi bạn muốn dùng là gì?",
                "Hình thức tạo đề thi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pizzas,
                pizzas[0]);
        
        newDialogRandomExam = new RandomExam(null, true);
        
        if ("Tạo đề tự động".equals(CreateExamForm)){
            loadSubjectCreate(newDialogRandomExam.subjects);
            loadChapterRandomCreate(0);
            newDialogRandomExam.setVisible(true);
        }
        if ("Tạo đề bằng tay".equals(CreateExamForm)){
            loadSubjectCreate(newDialogSelectedExam.subjects);
            loadChapterSelectedCreate(0);
            loadTableQuestionsContent();
            newDialogSelectedExam.setVisible(true);
        }
    }
    
    public static void sortTable(){
        DefaultTableModel sortedTable = new DefaultTableModel();
        DefaultTableModel table = (DefaultTableModel) newDialogRandomExam.table.getModel();
        String[] colsName = {"Chương", "Dạng câu hỏi", "Độ khó", "Số lượng"};
        sortedTable.setColumnIdentifiers(colsName);
        
        boolean[] check;
        check = new boolean[table.getRowCount()];
        for(int i = 0; i < check.length; ++i) check[i] = true;
        
        for(int i = 0; i < table.getRowCount(); ++i)
            if(check[i]){
                check[i] = false;
                String[] row = {table.getValueAt(i, 0).toString(), table.getValueAt(i, 1).toString(), table.getValueAt(i, 2).toString(), table.getValueAt(i, 3).toString()};
                for(int j = i+1; j < table.getRowCount(); ++j)
                    if ((check[j]) && (row[0].equals(table.getValueAt(j, 0))) && (row[1].equals(table.getValueAt(j, 1))) && (row[2].equals(table.getValueAt(j, 2)))){
                        row[3] = Integer.toString(Integer.parseInt(row[3]) + Integer.parseInt(table.getValueAt(j, 3).toString()));
                        check[j] = false;
                    }
                sortedTable.addRow(row);
            }
        newDialogRandomExam.table.setModel(sortedTable);
    }
    
    public static void insertQuestionsRandomCreate(){
        DefaultTableModel tableModel = (DefaultTableModel) newDialogRandomExam.table.getModel();
        String[] colsName = {"Chương", "Dạng câu hỏi", "Độ khó", "Số lượng"};
        tableModel.setColumnIdentifiers(colsName);
        int count1 = 0, count2 = 0;
        String numberMultipleChoice = newDialogRandomExam.numberMultipleChoice.getText();
        String numberEssay = newDialogRandomExam.numberEssay.getText();
        String message = "";
        int numberMultipleChoiceUsed = 0;
        int numberEssayUsed = 0;
        
        if (numberMultipleChoice.equals("")) numberMultipleChoice += "0";
        if (numberEssay.equals("")) numberEssay += "0";
        
        for(int i = 0; i < tableModel.getRowCount(); ++i){
            if (tableModel.getValueAt(i, 1).equals("Trắc nghiệm") 
            && tableModel.getValueAt(i, 0).equals(newDialogRandomExam.chapters.getSelectedItem().toString()) 
            && tableModel.getValueAt(i, 2).equals(newDialogRandomExam.level1.getSelectedItem().toString())){
                numberMultipleChoiceUsed += Integer.parseInt(tableModel.getValueAt(i, 3).toString());
            }
            
            if (tableModel.getValueAt(i, 1).equals("Tự luận") 
            && tableModel.getValueAt(i, 0).equals(newDialogRandomExam.chapters.getSelectedItem().toString()) 
            && tableModel.getValueAt(i, 2).equals(newDialogRandomExam.level2.getSelectedItem().toString())){
                numberEssayUsed += Integer.parseInt(tableModel.getValueAt(i, 3).toString());
            }
        }
        
        if(!numberMultipleChoice.equals("0")){
            if ((newDialogRandomExam.chapters.getSelectedIndex() == -1) || (newDialogRandomExam.level1.getSelectedIndex() == -1))
            {
                message += "\nchỉ có 0 câu trắc nghiệm cho loại này";
            }
            else{
                String[] row1 = {newDialogRandomExam.chapters.getSelectedItem().toString(), "Trắc nghiệm", newDialogRandomExam.level1.getSelectedItem().toString(), numberMultipleChoice};
                for(int i = 0; i < SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().size(); ++i){
                    if ((SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().get(i).getChapter().equals(newDialogRandomExam.chapters.getSelectedItem()))
                    && (SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().get(i).getLevel().toString().equals(newDialogRandomExam.level1.getSelectedItem()))) count1++;
                }

                if (count1 - numberMultipleChoiceUsed >= Integer.parseInt(numberMultipleChoice)){
                    tableModel.addRow(row1);
                }
                else{
                    message += "\nchỉ có " + Integer.toString(count1 - numberMultipleChoiceUsed) + " câu trắc nghiệm cho loại này";
                    numberMultipleChoice = "0";
                }
            }
        }
        if(!numberEssay.equals("0")){
            if ((newDialogRandomExam.chapters.getSelectedIndex() == -1) || (newDialogRandomExam.level2.getSelectedIndex() == -1))
            {
                message += "\nchỉ có 0 câu tự luận cho loại này";
            }
            else{
                String[] row2 = {newDialogRandomExam.chapters.getSelectedItem().toString(), "Tự luận", newDialogRandomExam.level2.getSelectedItem().toString(), numberEssay};
                for(int i = 0; i < SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().size(); ++i){
                    if ((SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().get(i).getChapter().equals(newDialogRandomExam.chapters.getSelectedItem()))
                    && (SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().get(i).getLevel().toString().equals(newDialogRandomExam.level2.getSelectedItem()))) count2++;
                }

                if (count2 - numberEssayUsed >= Integer.parseInt(numberEssay)){
                    tableModel.addRow(row2);
                }
                else{
                    message += "\nchỉ có " + Integer.toString(count2 - numberEssayUsed) + " câu tự luận cho loại này";
                    numberEssay = "0";
                }
            }
        }
        
        if (!message.equals("")){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, message);
        }

        newDialogRandomExam.sumQuestions.setText(Integer.toString(Integer.parseInt(newDialogRandomExam.sumQuestions.getText()) + Integer.parseInt(numberMultipleChoice) + Integer.parseInt(numberEssay)));
        newDialogRandomExam.table.setModel(tableModel);
        sortTable();
    }
    
    public static void deleteQuestionsRandomCreate(JTable table){
        DefaultTableModel deletedTable = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        
        if(selectedRow != -1){
            newDialogRandomExam.sumQuestions.setText(Integer.toString(Integer.parseInt(newDialogRandomExam.sumQuestions.getText()) - Integer.parseInt(table.getValueAt(selectedRow, 3).toString())));
            deletedTable.removeRow(selectedRow);
            newDialogRandomExam.table.setModel(deletedTable);
        }
    }
    
    public static void newRandomExam(JTable table){
        DefaultTableModel tableModel = new DefaultTableModel();
        
        String[] colsName = {"Chương", "Dạng câu hỏi", "Độ khó", "Số lượng"};
        tableModel.setColumnIdentifiers(colsName);
        
        newDialogRandomExam.nameExam.setText("");
        newDialogRandomExam.time.setText("");
        newDialogRandomExam.numberMultipleChoice.setText("");
        newDialogRandomExam.numberEssay.setText("");
        newDialogRandomExam.table.setModel(tableModel);
        
        loadSubjectCreate(newDialogRandomExam.subjects);
        loadChapterRandomCreate(0);
    }
    
    public static boolean createRandomExam(JTable table){
        
        if(newDialogRandomExam.nameExam.getText().equals("")){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "Vui lòng điền tên đề thi");
            return false;
        }
        else if(newDialogRandomExam.time.getText().equals("")){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "Vui lòng điền thời gian thi");
            return false;
        }
        else if(newDialogRandomExam.table.getRowCount() == 0){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "chưa có câu hỏi nào trong đề thi");
            return false;
        }
        else{
            Exam newExam = new Exam();
            newExam.setNameExam(newDialogRandomExam.nameExam.getText());
            newExam.setNameSubject(newDialogRandomExam.subjects.getSelectedItem().toString());
            //SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
            //newExam.setCreateday(timeFormat.format(new Date(System.currentTimeMillis())));
            newExam.setCreateday(new Date(System.currentTimeMillis()));
            newExam.setTimeExam(Integer.parseInt(newDialogRandomExam.time.getText()));
            
            for(int i = 0; i < table.getRowCount(); ++i){
                questions = new ArrayList();
                
                if(table.getValueAt(i, 1).equals("Trắc nghiệm"))
                    for(int j = 0; j < SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().size(); ++j)
                        if ((SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().get(j).getChapter().equals(table.getValueAt(i, 0)))
                        && (SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().get(j).getLevel().toString().equals(table.getValueAt(i, 2)))){
                            questions.add(SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getMultipleChoices().get(j));
                        }
                
                if(table.getValueAt(i, 1).equals("Tự luận"))
                    for(int j = 0; j < SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().size(); ++j)
                        if ((SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().get(j).getChapter().equals(table.getValueAt(i, 0)))
                        && (SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().get(j).getLevel().toString().equals(table.getValueAt(i, 2)))){
                            questions.add(SubjectController.subjects.getSubjects().get(newDialogRandomExam.subjects.getSelectedIndex()).getEssays().get(j));
                        }
                
                int[] index, value;
                Random rd = new Random();
                index = new int[questions.size()];
                value = new int[questions.size()];
                for(int j = 0; j < questions.size(); ++j){
                    value[j] = rd.nextInt(100);
                    index[j] = j;
                }
                for(int i1 = 0; i1 < value.length; ++i1)
                    for(int i2 = i1+1; i2 < value.length; ++i2)
                        if(value[i1] < value[i2])
                        {
                            int t = value[i1]; value[i1] = value[i2]; value[i2] = t;
                            t = index[i1]; index[i1] = index[i2]; index[i2] = t;
                        }
                for(int j = 0; j < Integer.parseInt(table.getValueAt(i, 3).toString()); ++j) newExam.setQuestion(questions.get(index[j]));
            }
            
            Exams editExams = ExamModel.readExam();
            editExams.setExam(newExam);
            ExamModel.writeExam(editExams);
            return true;
        }  
    }
    
    //selected Exam
    
    public static void loadChapterSelectedCreate(int index){
        newDialogSelectedExam.chapters.removeAllItems();
        DefaultTableModel tableQuestions = (DefaultTableModel) newDialogSelectedExam.tableQuestions.getModel();
        DefaultTableModel table = (DefaultTableModel) newDialogSelectedExam.table.getModel();
        
        newDialogSelectedExam.nameExam.setText("");
        newDialogSelectedExam.time.setText("");
        while(table.getRowCount() != 0) table.removeRow(0);
        selectedExam = new Exam();
        newDialogSelectedExam.sumQuestions.setText(Integer.toString(table.getRowCount()));
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().get(index).getChapters().size(); ++i) {
            newDialogSelectedExam.chapters.addItem(SubjectController.subjects.getSubjects().get(index).getChapters().get(i).getNameChapter());
        }
    }
    
    public static void loadTableQuestionsContent(){
        
        DefaultTableModel tableQuestions = (DefaultTableModel) newDialogSelectedExam.tableQuestions.getModel();
        questions = new ArrayList();
        
        while(tableQuestions.getRowCount() != 0) tableQuestions.removeRow(0);
        
        if(newDialogSelectedExam.kindOfQuestions.getSelectedItem().equals("Trắc nghiệm")){
            for(int i = 0; i < SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getMultipleChoices().size(); ++i)
                if(SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getMultipleChoices().get(i).getChapter().equals(newDialogSelectedExam.chapters.getSelectedItem())){
                    String[] row = {SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getMultipleChoices().get(i).getLevel().toString(), SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getMultipleChoices().get(i).getContentQuestion()};  
                    tableQuestions.addRow(row);
                    questions.add(SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getMultipleChoices().get(i));
                }
        }
        if(newDialogSelectedExam.kindOfQuestions.getSelectedItem().equals("Tự luận")){
            for(int i = 0; i < SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getEssays().size(); ++i)
                if(SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getEssays().get(i).getChapter().equals(newDialogSelectedExam.chapters.getSelectedItem())){
                    String[] row = {SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getEssays().get(i).getLevel().toString(), SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getEssays().get(i).getContentQuestion()};  
                    tableQuestions.addRow(row);
                    questions.add(SubjectController.subjects.getSubjects().get(newDialogSelectedExam.subjects.getSelectedIndex()).getEssays().get(i));
                }
        }
    }
    
    public static void insertQuestionsSelectedCreate(int index){
        DefaultTableModel table = (DefaultTableModel) newDialogSelectedExam.table.getModel();
        DefaultTableModel tableQuestions = (DefaultTableModel) newDialogSelectedExam.tableQuestions.getModel();     
        String[] row = {newDialogSelectedExam.chapters.getSelectedItem().toString(), newDialogSelectedExam.kindOfQuestions.getSelectedItem().toString(), tableQuestions.getValueAt(index, 0).toString(), tableQuestions.getValueAt(index, 1).toString()};
        table.addRow(row); 
        
        selectedExam.setQuestion(questions.get(index));
        newDialogSelectedExam.sumQuestions.setText(Integer.toString(table.getRowCount()));
        //System.out.println(selectedExam.getQuestions().size());
    }
    
    public static void newSelectedExam(){
        newDialogSelectedExam.sumQuestions.setText("0");
        loadSubjectCreate(newDialogSelectedExam.subjects);
        loadChapterSelectedCreate(0);
        loadTableQuestionsContent();
    }
    
    public static void deleteQuestionsSelectedCreate(int index){
        DefaultTableModel table = (DefaultTableModel) newDialogSelectedExam.table.getModel();
        
        selectedExam.getQuestions().remove(index);
        table.removeRow(index);
        newDialogSelectedExam.sumQuestions.setText(Integer.toString(table.getRowCount()));
    }
    
    public static boolean createSelectedExam(){
        if(newDialogSelectedExam.nameExam.getText().equals("")){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "Vui lòng điền tên đề thi");
            return false;
        }
        else if(newDialogSelectedExam.time.getText().equals("")){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "Vui lòng điền thời gian thi");
            return false;
        }
        else if(newDialogSelectedExam.table.getRowCount() == 0){
            JFrame frame = new JFrame("error");
            JOptionPane.showMessageDialog(frame, "chưa có câu hỏi nào trong đề thi");
            return false;
        }
        else{
            Exam newExam = new Exam();
            
            newExam.setNameExam(newDialogSelectedExam.nameExam.getText());
            newExam.setNameSubject(newDialogSelectedExam.subjects.getSelectedItem().toString());
            //SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
            //newExam.setCreateday(timeFormat.format(new Date(System.currentTimeMillis())));
            newExam.setCreateday(new Date(System.currentTimeMillis()));
            newExam.setTimeExam(Integer.parseInt(newDialogSelectedExam.time.getText()));
            
            for(int i = 0; i < selectedExam.getQuestions().size(); ++i){
                newExam.setQuestion(selectedExam.getQuestions().get(i));
            }
            
            Exams editExams = ExamModel.readExam();
            editExams.setExam(newExam);
            ExamModel.writeExam(editExams);
            return true;
        }
    }
}
