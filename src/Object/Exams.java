/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Duc Dung Dan
 */
public class Exams implements Serializable {
    private ArrayList<Exam> exams = new ArrayList<>();

    public ArrayList<Exam> getExams() {
        return exams;
    }
    
    public Exam getExam(int i) {
        if (i >= 0 && i < this.exams.size()) {
            return exams.get(i);
        } else {
            return null;
        }
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }
    
    public void setExam(Exam subject) {
        this.exams.add(subject);
    }

    public void removeExam(int i) {
        if(i >= 0 && i < this.exams.size()) {
            this.exams.remove(i);
        }
    }
}
