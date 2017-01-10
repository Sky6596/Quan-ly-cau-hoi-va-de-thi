/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ExamModel;
import Object.Answer;
import Object.Essay;
import Object.Exam;
import Object.Exams;
import Object.MultipleChoice;
import Object.Question;
import Views.ExportPath;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author 8TITTIT8
 */
public class ExportController {
    
    private static JTable tableExam = new JTable();
    private static DefaultTableModel model;
    public static Exams exams = new Exams();
    
    public static void showSavePath(JTable table){
        tableExam = table;
        ExportPath exportPath = new ExportPath();
    }
    
    public static void exportExam(String path){
        model = (DefaultTableModel) tableExam.getModel();
        int selectRow = tableExam.getSelectedRow();
        
        if (selectRow != -1) {
            int idExam = (int)model.getValueAt(selectRow, 0) - 1;
            exams = ExamModel.readExam();
            Exam ex = exams.getExam(idExam);
            
            try{
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();
                BaseFont f = BaseFont.createFont("/font/vuArial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font titleExamFont = new Font(f, 25.0f, Font.BOLD);
                Font titlePartFont = new Font(f, 18.0f, Font.BOLD);
                Font headFont = new Font(f, 13.0f, Font.BOLD);
                Font suggestionFont = new Font(f, 13.0f, Font.ITALIC);
                Font contentFont = new Font(f, 13.0f, Font.NORMAL);
                Paragraph align = new Paragraph(" ");
                Paragraph title = new Paragraph(ex.getNameExam(), titleExamFont);
                title.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(title);
                document.add(align);
                
                ArrayList<Question> question = ex.getQuestions();
            
                boolean haveMultipleChoice = false; 
                boolean haveEssay = false;          
                // Kiểm tra xem có phần tự luận không
                for (Question q : question){
                    if(q instanceof Essay){
                        haveEssay = true;
                    }
                    // Kiểm tra xem có phần trắc nghiệm không}
                    else {
                        haveMultipleChoice = true;
                    }
                    if (haveEssay && haveMultipleChoice) break;
                }
                
                int count;
                if (haveMultipleChoice){
                    count = 0;
                    Paragraph titlePart = new Paragraph("Trắc nghiệm", titlePartFont);
                    document.add(align);
                    document.add(titlePart);
                    document.add(align);
                    for (Question q : question)
                        if (q instanceof MultipleChoice){
                            count++;
                            Phrase numberQuestion = new Phrase("Câu " + count + ": ", headFont);
                            Phrase contentQuestion = new Phrase(q.getContentQuestion(), contentFont);
                            Paragraph questionParagraph = new Paragraph();
                            questionParagraph.add(numberQuestion);
                            questionParagraph.add(contentQuestion);
                            document.add(questionParagraph);

                            MultipleChoice mc = (MultipleChoice) q;
                            ArrayList<Answer> answers = mc.getAnswers();
                            
                            boolean ok = true;
                            for (int i = 0; i < answers.size(); ++i) {
                                Answer answer = answers.get(i);
                                if (answer.getContentAnswer().length() > 30) ok = false;
                            }
                            
                            if (ok == true){
                                PdfPTable table = new PdfPTable(2); 
                                for (int i = 0; i < answers.size(); ++i) {
                                    Answer answer = answers.get(i);
                                    PdfPCell answerParagraph = new PdfPCell(new Paragraph((char) (65 + i) + ". " + answer.getContentAnswer(), contentFont));
                                    answerParagraph.setBorder(Rectangle.NO_BORDER);
                                    table.addCell(answerParagraph);
                                }
                                document.add(table);
                            }
                            else{
                                PdfPTable table = new PdfPTable(1); 
                                for (int i = 0; i < answers.size(); ++i) {
                                    Answer answer = answers.get(i);
                                    PdfPCell answerParagraph = new PdfPCell(new Paragraph((char) (65 + i) + ". " + answer.getContentAnswer(), contentFont));
                                    answerParagraph.setBorder(Rectangle.NO_BORDER);
                                    table.addCell(answerParagraph);
                                }
                                document.add(table);
                            }
                        }
                }
                
                if (haveEssay){
                    count = 0;
                    Paragraph titlePart = new Paragraph("Tự Luận", titlePartFont);
                    document.add(align);
                    document.add(titlePart);
                    document.add(align);
                    for (Question q : question)
                        if (q instanceof Essay){
                            count++;
                            Phrase numberQuestion = new Phrase("Câu " + count + ": ", headFont);
                            Phrase contentQuestion = new Phrase(q.getContentQuestion(), contentFont);
                            Paragraph questionParagraph = new Paragraph();
                            questionParagraph.add(numberQuestion);
                            questionParagraph.add(contentQuestion);

                            Essay es = (Essay) q;
                            Phrase headerSuggestion = new Phrase("Gợi ý: ", suggestionFont);
                            Phrase contentSuggestion = new Phrase(es.getSuggest(), contentFont);
                            Paragraph suggestion = new Paragraph();
                            suggestion.add(headerSuggestion);
                            suggestion.add(contentSuggestion);
                            document.add(questionParagraph);
                            document.add(suggestion);
                        }
                }
                
                document.close();
            }catch(FileNotFoundException exp){
                exp.printStackTrace();
            }catch(DocumentException exp){
                exp.printStackTrace();
            }catch(IOException exp){
                exp.printStackTrace();
            }    
        }
    }
}
