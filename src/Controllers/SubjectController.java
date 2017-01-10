/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import Models.SubjectsModel;
import Object.*;
import Views.NewChapter;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc Dung Dan
 */
public class SubjectController {
    
    public static Subjects subjects = new Subjects();
    
    public static void viewSubject(JTable tableSubjects) {
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        subjects = SubjectsModel.readSubjects();
        
        for (int i = 0; i < subjects.getSubjects().size(); ++i) {
            model.addRow(new Object[]{i + 1, subjects.getSubject(i).getNameSubject(), subjects.getSubject(i).getChapters().size()});
        }
    }
    
    public static void viewChapter(JTable tableSubjects, JTable tableChapters) {
        DefaultTableModel model = (DefaultTableModel) tableChapters.getModel();
        int selectRow = tableSubjects.getSelectedRow();
        if(selectRow != -1) {
            int rowCount = model.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            
            Subject subject = subjects.getSubject(selectRow);
            
            for (int i = 0; i < subject.getChapters().size(); ++i) {
                model.addRow(new Object[]{i + 1, subject.getChapter(i).getNameChapter()});
            }
        }
    }

    public static void addSubject(JTable tableSubjects) {
        JFrame frame = new JFrame("Question form");
        String newNameSubject = (String) JOptionPane.showInputDialog(frame,
                "Nhập tên môn học",
                "New Subject",
                JOptionPane.INFORMATION_MESSAGE);
        if(newNameSubject != null && newNameSubject.trim().length() != 0) {
            for(int i = 0; i < subjects.getSubjects().size(); ++i) {
                if(ImportController.removeAccent(subjects.getSubject(i).getNameSubject()).equals(ImportController.removeAccent(newNameSubject))) {
                    JOptionPane.showMessageDialog(null, "Môn học đã có trong dữ liệu", "Thông báo", 2);
                    return;
                }
            }
            Subject newSubject = new Subject();
            newSubject.setNameSubject(newNameSubject);
            subjects.setSubject(newSubject);
            SubjectsModel.writeSubjects(subjects);
            DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
            model.addRow(new Object[] {model.getColumnCount() + 1, newSubject.getNameSubject(), 0});
            JOptionPane.showMessageDialog(null, "Thêm môn học thành công", "Thông báo", 2);
        }
    }
    
    public static void viewAddChapter(JTable tableSubjects, JTable tableChapter) {
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int index = tableSubjects.getSelectedRow();
        NewChapter newChapter = new NewChapter(null, true);
        QuestionController.loadSubjects(newChapter.subject);
        if(index == -1) {
            newChapter.subject.setSelectedIndex(0);
        } else {
            newChapter.subject.setSelectedIndex(index);
        }
        newChapter.update.setVisible(false);
        
        newChapter.add.addActionListener((ActionEvent ae) -> {
            if (newChapter.input.getText().trim().length() != 0) {
                Subject subject = subjects.getSubject(newChapter.subject.getSelectedIndex());
                for (int i = 0; i < subject.getChapters().size(); ++i) {
                    if (ImportController.removeAccent(subject.getChapter(i).getNameChapter()).equals(ImportController.removeAccent(newChapter.input.getText().trim()))) {
                        JOptionPane.showMessageDialog(null, "Chương này đã có trong môn học", "Thông báo", 2);
                        return ;
                    }
                }
                Chapter chapter = new Chapter();
                chapter.setNameChapter(newChapter.input.getText().trim());
                subjects.getSubject(newChapter.subject.getSelectedIndex()).setChapter(chapter);
                SubjectsModel.writeSubjects(subjects);
                JOptionPane.showMessageDialog(null, "Thêm chương vào môn học thành công", "Thông báo", 2);
                if(index == newChapter.subject.getSelectedIndex()) {
                    DefaultTableModel modelChapter = (DefaultTableModel) tableChapter.getModel();
                    modelChapter.addRow(new Object[]{modelChapter.getRowCount() + 1, newChapter.input.getText().trim()});
                }
                newChapter.dispose();
                
            }
        });
        
        newChapter.setVisible(true);

    }
    
    public static void viewEditChapter(JTable tableSubjects, JTable tableChapter) {
        NewChapter newChapter = new NewChapter(null, true);
        
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int selectRow = tableSubjects.getSelectedRow();
        DefaultTableModel modelChapter = (DefaultTableModel) tableChapter.getModel();
        int selectRowChapter = tableChapter.getSelectedRow();
        
        QuestionController.loadSubjects(newChapter.subject);
        newChapter.subject.setSelectedIndex(selectRow);
        newChapter.subject.setEnabled(false);
        newChapter.input.setText((String) modelChapter.getValueAt(selectRowChapter, 1));
                
        newChapter.add.setVisible(false);
        
        
        newChapter.update.addActionListener((ActionEvent ae) -> {
            if (newChapter.input.getText().trim().length() != 0) {
                Subject subject = subjects.getSubject(newChapter.subject.getSelectedIndex());
                for (int i = 0; i < subject.getChapters().size(); ++i) {
                    if (ImportController.removeAccent(subject.getChapter(i).getNameChapter()).equals(ImportController.removeAccent(newChapter.input.getText().trim()))) {
                        JOptionPane.showMessageDialog(null, "Chương này đã có trong môn học", "Thông báo", 2);
                        return ;
                    }
                }
                
                subject.getChapter(selectRowChapter).setNameChapter(newChapter.input.getText().trim());
                
                SubjectsModel.writeSubjects(subjects);
                JOptionPane.showMessageDialog(null, "Sửa tên chương thành công", "Thông báo", 2);
                
                if(selectRow == newChapter.subject.getSelectedIndex()) {
                    modelChapter.setValueAt(newChapter.input.getText().trim(), selectRowChapter, 1);
                } else {
                    modelChapter.removeRow(selectRowChapter);
                }
                
                newChapter.dispose();
            }
            
        });

        newChapter.setVisible(true);
    }
    
    public static void editSubject(JTable tableSubjects) {
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int selectRow = tableSubjects.getSelectedRow();
        
        JFrame frame = new JFrame("Question form");
        String newNameSubject = (String) JOptionPane.showInputDialog(frame,
                "Nhập tên môn học",
                "New Subject",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                model.getValueAt(selectRow, 1));
        if (newNameSubject != null && newNameSubject.trim().length() != 0 && newNameSubject != model.getValueAt(selectRow, 1)) {
            for (int i = 0; i < subjects.getSubjects().size(); ++i) {
                if (ImportController.removeAccent(subjects.getSubject(i).getNameSubject()).equals(ImportController.removeAccent(newNameSubject))) {
                    JOptionPane.showMessageDialog(null, "Môn học đã có trong dữ liệu", "Thông báo", 2);
                    return;
                }
            }
            
            subjects.getSubject(selectRow).setNameSubject(newNameSubject);
            SubjectsModel.writeSubjects(subjects);
            model.setValueAt(newNameSubject, selectRow, 1);
            JOptionPane.showMessageDialog(null, "Đổi tên môn học thành công", "Thông báo", 2);

        }
    }
    
    public static void deleteSubject(JTable tableSubjects) {
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int selectRow = tableSubjects.getSelectedRow();
        String question = "Bạn muốn môn học này \n Chú ý sẽ xóa tất cả các câu hỏi thuộc môn học";
        if (JOptionPane.showConfirmDialog(null, question, "Thông báo", 2) == 0) {
            subjects.removeSubject(selectRow);
            SubjectsModel.writeSubjects(subjects);
            model.removeRow(selectRow);
        }
    }
    
    public static void deleteChapter(JTable tableSubjects, JTable tableChapter) {
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        int selectRow = tableSubjects.getSelectedRow();
        String question = "Bạn muốn chương này \n Chú ý sẽ xóa tất cả các câu hỏi thuộc chương đó";
        if (JOptionPane.showConfirmDialog(null, question, "Thông báo", 2) == 0) {
            DefaultTableModel modelChapter = (DefaultTableModel) tableChapter.getModel();
            int selectRowChapter = tableChapter.getSelectedRow();
            Subject subject = subjects.getSubject(selectRow);
            subject.removeChapter(selectRowChapter);
            String nameChapterDelete = (String) modelChapter.getValueAt(selectRowChapter, 1);
            for(int i = 0; i < subject.getEssays().size(); ++i) {
                if(nameChapterDelete.equals(subject.getEssay(i).getChapter())) {
                    subject.removeEssay(i);
                }
            }
            
            for (int i = 0; i < subject.getMultipleChoices().size(); ++i) {
                if (nameChapterDelete.equals(subject.getMultipleChoice(i).getChapter())) {
                    subject.getMultipleChoice(i);
                }
            }
            
            modelChapter.removeRow(selectRowChapter);
            SubjectsModel.writeSubjects(subjects);
        }
    }

}
