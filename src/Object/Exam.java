/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Duc Dung Dan
 */
public class Exam implements Serializable{
    private String nameExam;
    private String nameSubject;
    private Date createday;
    private int timeExam;
    private ArrayList<Question> questions = new ArrayList<>();
    
    public Exam() {
        createday = new Date();
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public void setNameSubject(String nameSubject){
        this.nameSubject = nameSubject;
    }
    
    public void setCreateday(Date createday) {
        this.createday = createday;
    }

    public void setTimeExam(int timeExam) {
        this.timeExam = timeExam;
    }
    
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    
    public void setQuestion(Question question) {
        this.questions.add(question);
    }

    public String getNameExam() {
        return nameExam;
    }

    public String getNameSubject(){
        return nameSubject;
    }
    
    public Date getCreateday() {
        return createday;
    }
    
    public int getTimeExam(){
        return timeExam;
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
    public Question getQuestion(int i) {
        return questions.get(i);
    }
    
    public void updateQuestion(int i, Question question) {
        if(i >= 0 && i < this.questions.size()) {
            this.questions.set(i, question);
        }
    }
}
