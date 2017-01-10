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
public class Subjects implements Serializable {
    private ArrayList<Subject> subjects = new ArrayList<>();

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
    
    public Subject getSubject(int i) {
        return subjects.get(i);
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setSubject(Subject subject) {
        this.subjects.add(subject);
    }
    
    public void removeSubject(int i) {
        if (i >= 0 && i < this.subjects.size()) {
            this.subjects.remove(i);
        }
    }
}
