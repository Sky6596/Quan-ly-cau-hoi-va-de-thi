/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import java.io.Serializable;

/**
 *
 * @author Duc Dung Dan
 */
public abstract class Question implements Serializable {
    private String contentQuestion;
    private Number level;
    private String chapter ;

    public void setChapter(String chapter) {
        if (chapter.trim().length() != 0) {
            this.chapter = chapter.trim();
        }
    }

    public String getChapter() {
        return chapter;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public Number getLevel() {
        return level;
    }

    public void setContentQuestion(String contentQuestion) {
        if(contentQuestion.trim().length() != 0) {
            this.contentQuestion = contentQuestion.trim();
        }
    }

    public void setLevel(int level) {
        if(level > 0 && level < 6) {
            this.level = level;
        }
    }
    
    public abstract String printfQuestion();
}
