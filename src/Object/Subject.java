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
public class Subject implements Serializable {
    private String nameSubject;
    private ArrayList<Chapter> chapters = new ArrayList<>();
    private ArrayList<MultipleChoice> multipleChoices = new ArrayList<>();
    private ArrayList<Essay> essays = new ArrayList<>();

    public void setMultipleChoices(ArrayList<MultipleChoice> multipleChoices) {
        this.multipleChoices = multipleChoices;
    }
    
    public void setMultipleChoice(MultipleChoice multipleChoice) {
        this.multipleChoices.add(multipleChoice);
    }    

    public void setEssays(ArrayList<Essay> essays) {
        this.essays = essays;
    }
    
    public void setEssay(Essay essay) {
        this.essays.add(essay);
    }

    public ArrayList<MultipleChoice> getMultipleChoices() {
        return multipleChoices;
    }
    
    public MultipleChoice getMultipleChoice(int i) {
        return multipleChoices.get(i);
    }
    
    public Essay getEssay(int i) {
        return this.essays.get(i);
    }

    public ArrayList<Essay> getEssays() {
        return essays;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }
    
    public Chapter getChapter(int i) {
        if(i >= 0 && i < this.chapters.size()) {
            return chapters.get(i);
        } else {
            return chapters.get(0);
        }
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }
    
    public void setChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }
    
    public void removeChapter(int i) {
        if (i >= 0 && i < this.chapters.size()) {
            this.chapters.remove(i);
        }
    }
    
    public void removeMultipleChoice(int i) {
        if (i >= 0 && i < this.multipleChoices.size()) {
            this.multipleChoices.remove(i);
        }
    }
    
    public void removeEssay(int i) {
        if (i >= 0 && i < this.essays.size()) {
            this.essays.remove(i);
        }
    }
    
    public void updateEssay(int i, Essay essay) {
        if (i >= 0 && i < this.essays.size()) {
            this.essays.set(i, essay);
        }
    }
    
    public void updateMultipleChoice(int i, MultipleChoice multiplechoice) {
        if (i >= 0 && i < this.multipleChoices.size()) {
            this.multipleChoices.set(i, multiplechoice);
        }
    }
}
