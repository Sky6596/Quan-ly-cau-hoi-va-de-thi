/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.SubjectController.subjects;
import Models.SubjectsModel;
import Object.Answer;
import Object.Essay;
import Object.MultipleChoice;
import Views.InformationQuestion;
import Views.NewAnswer;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc Dung Dan
 */
public class QuestionController {
    public static InformationQuestion newDialogQuestion = new InformationQuestion(null, true);
    public static Essay essay;
    public static MultipleChoice multipleChoice;
    public static String questionForm;
    public static int idQuestion;
    
    public static void loadTableQuestion(JTable table) {
        subjects = SubjectsModel.readSubjects();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        for (int i = 0; i < subjects.getSubjects().size(); ++i) {
            for (int j = 0; j < subjects.getSubjects().get(i).getEssays().size(); ++j) {
                essay = subjects.getSubjects().get(i).getEssays().get(j);
                model.addRow(new Object[]{i * 1000 + j, essay.getContentQuestion(), essay.getChapter(), subjects.getSubjects().get(i).getNameSubject(), essay.getLevel(), "Tự luận"});

            }

            for (int j = 0; j < subjects.getSubjects().get(i).getMultipleChoices().size(); ++j) {
                multipleChoice = subjects.getSubjects().get(i).getMultipleChoices().get(j);
                model.addRow(new Object[]{i * 1000 + 500 + j, multipleChoice.getContentQuestion(), multipleChoice.getChapter(), subjects.getSubjects().get(i).getNameSubject(), multipleChoice.getLevel(), "Trắc nghiệm"});
            }
        }
    }
    
    public static void loadSubjects(JComboBox subjects) {
        subjects.removeAllItems();
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().size(); ++i) {
            subjects.addItem(SubjectController.subjects.getSubjects().get(i).getNameSubject());
        }
    }
    
    public static void loadChapter(int index) {
        newDialogQuestion.chapters.removeAllItems();
        
        for(int i = 0; i < SubjectController.subjects.getSubjects().get(index).getChapters().size(); ++i) {
            newDialogQuestion.chapters.addItem(SubjectController.subjects.getSubjects().get(index).getChapters().get(i).getNameChapter());
        }
    }
    
    public static void viewNewQuestion() {
        String[] pizzas = {"Tự luận", "Trắc nghiệm"};
        JFrame frame = new JFrame("Question form");
        questionForm = (String) JOptionPane.showInputDialog(frame,
                "Hình thức câu hỏi bạn muốn tạo là gì?",
                "Hình thức câu hỏi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pizzas,
                pizzas[0]);
        
        newDialogQuestion = new InformationQuestion(null, true);

        if("Tự luận".equals(questionForm)) {
            essay = new Essay();
            newDialogQuestion.update.setVisible(false);
            newDialogQuestion.addAns.setVisible(false);

            JTextArea suggest = new JTextArea();
            suggest.setEnabled(true);
            JScrollPane JScr = new JScrollPane(suggest);
            JScr.setSize(newDialogQuestion.jPanel1.getPreferredSize().width + 25, newDialogQuestion.jPanel1.getPreferredSize().height);
            
            
            newDialogQuestion.jPanel1.removeAll();
            newDialogQuestion.jPanel1.setPreferredSize(JScr.getSize());
            newDialogQuestion.jPanel1.add(JScr);

            loadSubjects(newDialogQuestion.subjects);
            loadChapter(0);
            newDialogQuestion.setVisible(true);
        } else {
            if("Trắc nghiệm".equals(questionForm)) {
                multipleChoice = new MultipleChoice();
                newDialogQuestion.update.setVisible(false);
                newDialogQuestion.answer.removeAll();
                newDialogQuestion.answer.setPreferredSize(new Dimension(newDialogQuestion.answer.getPreferredSize().width, 0));
                loadSubjects(newDialogQuestion.subjects);
                loadChapter(0);
                newDialogQuestion.setVisible(true);
            }
        }
    }
    
    public static void viewQuestion(JTable table, JPanel question) {
        question.removeAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectRow = table.getSelectedRow();
        int id = (int) model.getValueAt(selectRow, 0);
        String content;
        if (model.getValueAt(selectRow, 5) == "Tự luận") {
            content = subjects.getSubject(id / 1000).getEssay(id % 1000).printfQuestion();
        } else {
            content = subjects.getSubject(id / 1000).getMultipleChoice(id % 1000 - 500).printfQuestion();
        }

        JLabel jlabelContent = new JLabel("<html><body style='width: " + question.getPreferredSize().width + "'>" + content + "</body></html>", SwingConstants.LEFT);

        question.setLayout(new FlowLayout(FlowLayout.LEFT));
        question.add(jlabelContent);
        question.updateUI();
    }
    
    public static void viewEditQuestion(JTable tableQuestion) {
        
        //load question edit
        DefaultTableModel model = (DefaultTableModel) tableQuestion.getModel();
        int selectRow = tableQuestion.getSelectedRow();

        
        
        if (selectRow != -1) {
            idQuestion = (int) model.getValueAt(selectRow, 0);
            newDialogQuestion = new InformationQuestion(null, true);

            if(model.getValueAt(selectRow, 5) == "Trắc nghiệm") {
                questionForm = "Trắc nghiệm";
                multipleChoice = subjects.getSubject(idQuestion / 1000).getMultipleChoice(idQuestion % 1000 - 500);
                newDialogQuestion.create.setVisible(false);
                newDialogQuestion.answer.removeAll();
                newDialogQuestion.answer.setPreferredSize(new Dimension(newDialogQuestion.answer.getPreferredSize().width, 0));

                    loadSubjects(newDialogQuestion.subjects);
                    newDialogQuestion.subjects.setSelectedIndex(idQuestion / 1000);
                    loadChapter(idQuestion / 1000);
                    newDialogQuestion.chapters.setSelectedItem(multipleChoice.getChapter());

                    newDialogQuestion.contentQuestion.setText(multipleChoice.getContentQuestion());
                    newDialogQuestion.level.setSelectedItem(multipleChoice.getLevel());

                    ArrayList<Answer> answers = multipleChoice.getAnswers();
                    for (int i = 0; i < answers.size(); ++i) {
                        NewAnswer newAns = new NewAnswer();
                        newDialogQuestion.answer.setLayout(new FlowLayout());
                        Dimension size = newAns.background.getPreferredSize();
                        newDialogQuestion.answer.setPreferredSize(new Dimension(newDialogQuestion.answer.getPreferredSize().width, newDialogQuestion.answer.getPreferredSize().height + size.height));
                        newAns.contentAnswer.setText(answers.get(i).getContentAnswer());
                        newAns.TF.setState(answers.get(i).getAccuracy());
                        newDialogQuestion.answer.add(newAns.background);
                        newDialogQuestion.answer.updateUI();
                    }
                
            } else {
                questionForm = "Tự luận";
                newDialogQuestion.addAns.setVisible(false);
                essay = subjects.getSubject(idQuestion / 1000).getEssay(idQuestion % 1000);
                newDialogQuestion.create.setVisible(false);

                loadSubjects(newDialogQuestion.subjects);
                newDialogQuestion.subjects.setSelectedIndex(idQuestion / 1000);
                loadChapter(idQuestion / 1000);
                newDialogQuestion.chapters.setSelectedItem(essay.getChapter());

                JTextArea suggest = new JTextArea(essay.getSuggest());
                suggest.setEnabled(true);
                JScrollPane JScr = new JScrollPane(suggest);
                JScr.setSize(newDialogQuestion.jPanel1.getPreferredSize().width + 25, newDialogQuestion.jPanel1.getPreferredSize().height);

                newDialogQuestion.jPanel1.removeAll();
                newDialogQuestion.jPanel1.setPreferredSize(JScr.getSize());
                newDialogQuestion.jPanel1.add(JScr);
                
                newDialogQuestion.contentQuestion.setText(essay.getContentQuestion());
                newDialogQuestion.level.setSelectedItem(essay.getLevel());

            }
            newDialogQuestion.setVisible(true);

            
        }
    }
    
    public static void viewNewAnswer() {
        multipleChoice.setAnswer(new Answer());
        NewAnswer newAns = new NewAnswer();
        newDialogQuestion.answer.setLayout(new FlowLayout());
        Dimension size = newAns.background.getPreferredSize();
        newDialogQuestion.answer.setPreferredSize(new Dimension(newDialogQuestion.answer.getPreferredSize().width, newDialogQuestion.answer.getPreferredSize().height + size.height + 5));
        newDialogQuestion.answer.add(newAns.background);
        newDialogQuestion.answer.updateUI();
    }

    
    public static Boolean createQuestion() {
        if("Trắc nghiệm".equals(questionForm)) {
            if (newDialogQuestion.answer.getComponentCount() > 3) {
                multipleChoice = new MultipleChoice();
                multipleChoice.setChapter((String) newDialogQuestion.chapters.getSelectedItem());
                multipleChoice.setLevel(Integer.parseInt((String) newDialogQuestion.level.getSelectedItem()));
                multipleChoice.setContentQuestion(newDialogQuestion.contentQuestion.getText());
                
                if(multipleChoice.getContentQuestion().trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầu đủ nội dung câu hỏi", "Thông báo", 3);
                    return false;
                }
                
                int soCauDung = 0;
                for (int i = 0; i < newDialogQuestion.answer.getComponentCount(); ++i) {
                    Answer newAns = new Answer();
                    JPanel ans = new JPanel();
                    JTextField content = new JTextField();
                    Checkbox TF = new Checkbox();
                    ans = (JPanel) newDialogQuestion.answer.getComponent(i);
                    content = (JTextField) ans.getComponent(1);
                    TF = (Checkbox) ans.getComponent(0);
                    newAns.setContentAnswer(content.getText());
                    newAns.setAccuracy(TF.getState());
                    if(TF.getState()) {
                        soCauDung++;
                    }
                    multipleChoice.setAnswer(newAns);
                }
                
                if(soCauDung == multipleChoice.getAnswers().size()) {
                    JOptionPane.showMessageDialog(null, "Không thể thêm câu hỏi mà tất cả phương đều chính xác", "Thông báo", 3);
                    return false;
                }
                
                if (soCauDung == 0) {
                    JOptionPane.showMessageDialog(null, "Không thể thêm câu hỏi mà tất cả phương đều sai", "Thông báo", 3);
                    return false;
                }
                
                for(int i = 0; i < SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).getMultipleChoices().size(); ++i) {
                    if(SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).getMultipleChoice(i).getContentQuestion().equals(multipleChoice.getContentQuestion())) {
                        JOptionPane.showMessageDialog(null, "Câu hỏi đã tồn tại", "Thông báo", 3);
                        return false;
                    }
                }
                
                
                SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).setMultipleChoice(multipleChoice);
                
                if (SubjectsModel.writeSubjects(SubjectController.subjects)) {
                    newDialogQuestion = new InformationQuestion(null, true);
                    JOptionPane.showMessageDialog(null, "Thêm câu hỏi thành công", "Thông báo", 3);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm câu hỏi thất bại", "Thông báo", 3);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Câu hỏi trắc nghiệm phải có ít nhất 4 đáp án trở lên", "Lỗi", 2);
                return false;
            }
        } else {
            JTextArea suggest = (JTextArea) (((JViewport) (((JScrollPane) newDialogQuestion.jPanel1.getComponent(0)).getViewport()))).getView();
            if (suggest.getText().length() > 0) {
                essay.setChapter((String) newDialogQuestion.chapters.getSelectedItem());
                essay.setLevel(Integer.parseInt((String) newDialogQuestion.level.getSelectedItem()));
                essay.setContentQuestion(newDialogQuestion.contentQuestion.getText());
                essay.setSuggest(suggest.getText());
                
                if (essay.getContentQuestion().trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầu đủ nội dung câu hỏi", "Thông báo", 3);
                    return false;
                }

                
                for(int i = 0; i < SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).getEssays().size(); ++i) {
                    if(SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).getEssay(i).getContentQuestion().equals(essay.getContentQuestion())) {
                        JOptionPane.showMessageDialog(null, "Câu hỏi đã tồn tại", "Lỗi", 2);
                        return false;
                    }
                }
                
                SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).setEssay(essay);

                
                if (SubjectsModel.writeSubjects(SubjectController.subjects)) {
                    newDialogQuestion = new InformationQuestion(null, true);
                    JOptionPane.showMessageDialog(null, "Thêm câu hỏi thành công", "Thông báo", 3);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm câu hỏi thất bại", "Thông báo", 3);
                    return false;
                }
            }
        }
        
        return false;
    }
    
    public static Boolean updateQuestion() {
        if ("Trắc nghiệm".equals(questionForm)) {
            if (newDialogQuestion.answer.getComponentCount() > 3) {
                multipleChoice.setChapter((String) newDialogQuestion.chapters.getSelectedItem());
                multipleChoice.setLevel(Integer.parseInt((String) newDialogQuestion.level.getSelectedItem()));
                multipleChoice.setContentQuestion(newDialogQuestion.contentQuestion.getText());
                for(int i = multipleChoice.getAnswers().size() - 1; i >= 0; --i) {
                    multipleChoice.getAnswers().remove(i);
                }
                for (int i = 0; i < newDialogQuestion.answer.getComponentCount(); ++i) {
                    Answer newAns = new Answer();
                    JPanel ans = new JPanel();
                    JTextField content = new JTextField();
                    Checkbox TF = new Checkbox();
                    ans = (JPanel) newDialogQuestion.answer.getComponent(i);
                    content = (JTextField) ans.getComponent(1);
                    TF = (Checkbox) ans.getComponent(0);
                    newAns.setContentAnswer(content.getText());
                    newAns.setAccuracy(TF.getState());
                    multipleChoice.setAnswer(newAns);
                }
                if(newDialogQuestion.subjects.getSelectedIndex() == idQuestion/1000) {
                    SubjectController.subjects.getSubject(idQuestion/1000).updateMultipleChoice(idQuestion%1000-500, multipleChoice);
                } else {
                    SubjectController.subjects.getSubject(idQuestion/1000).removeMultipleChoice(idQuestion%1000-500);
                    SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).setMultipleChoice(multipleChoice);
                }

                if (SubjectsModel.writeSubjects(SubjectController.subjects)) {
                    newDialogQuestion = new InformationQuestion(null, true);
                    JOptionPane.showMessageDialog(null, "Sửa câu hỏi thành công", "Thông báo", 3);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa câu hỏi thất bại", "Thông báo", 3);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Câu hỏi trắc nghiệm phải có ít nhất 4 đáp án trở lên", "Lỗi", 2);
                return false;
            }
        } else {
            JTextArea suggest = (JTextArea) (((JViewport) (((JScrollPane) newDialogQuestion.jPanel1.getComponent(0)).getViewport()))).getView();
            if (suggest.getText().length() > 0) {
                essay.setChapter((String) newDialogQuestion.chapters.getSelectedItem());
                essay.setLevel(Integer.parseInt((String) newDialogQuestion.level.getSelectedItem()));
                essay.setContentQuestion(newDialogQuestion.contentQuestion.getText());
                essay.setSuggest(suggest.getText());

                if (newDialogQuestion.subjects.getSelectedIndex() == idQuestion / 1000) {
                    SubjectController.subjects.getSubject(idQuestion / 1000).updateEssay(idQuestion % 1000, essay);
                } else {
                    SubjectController.subjects.getSubject(idQuestion / 1000).removeEssay(idQuestion % 1000);
                    SubjectController.subjects.getSubject(newDialogQuestion.subjects.getSelectedIndex()).setEssay(essay);
                }

                if (SubjectsModel.writeSubjects(SubjectController.subjects)) {
                    newDialogQuestion = new InformationQuestion(null, true);
                    JOptionPane.showMessageDialog(null, "Sửa câu hỏi thành công", "Thông báo", 3);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa câu hỏi thất bại", "Thông báo", 3);
                    return false;
                }
            }
        }

        return false;
    }
    
    public static void deleteAnswer(Component answer) {
        String question = "Bạn muốn xóa câu trả lời này";
        if (JOptionPane.showConfirmDialog(null, question, "Thông báo", 2) == 0) {
            int i = newDialogQuestion.answer.getComponentZOrder(answer);
            multipleChoice.removeAns(i);
            newDialogQuestion.answer.remove(i);
            newDialogQuestion.answer.updateUI();
        }
    }
    
    public static void deleteQuestion(JTable tableQuestion) {
        DefaultTableModel model = (DefaultTableModel) tableQuestion.getModel();
        int selectRow = tableQuestion.getSelectedRow();
        
        if (selectRow != -1) {
            idQuestion = (int) model.getValueAt(selectRow, 0);
            String question = "Bạn muốn xóa câu hỏi có id = " + idQuestion;
            if (JOptionPane.showConfirmDialog(null, question, "Thông báo", 2) == 0) {
                if(model.getValueAt(selectRow, 5) == "Trắc nghiệm") {
                    subjects.getSubject(idQuestion / 1000).removeMultipleChoice(idQuestion % 1000 - 500);
                    SubjectsModel.writeSubjects(subjects);
                    model.removeRow(selectRow);
                } else {
                    subjects.getSubject(idQuestion / 1000).removeEssay(idQuestion % 1000);
                    SubjectsModel.writeSubjects(subjects);
                    model.removeRow(selectRow);
                }
            }
        }
    }
    
}
