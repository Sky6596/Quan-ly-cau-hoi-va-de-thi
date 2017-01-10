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
public class Essay extends Question implements Serializable{
    private String suggest;

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        if (suggest.trim().length() != 0) {
            this.suggest = suggest.trim();
        }
    }
    
    @Override
    public String printfQuestion() {
        StringBuilder question = new StringBuilder();
        question.append("Chương - ").append(super.getChapter()).append("<br>");
        question.append("Độ khó: ").append(super.getLevel()).append("<br>");
        
        question.append("Đề: ").append(super.getContentQuestion());
        question.append("<br><br>");
        question.append("<font color='red'>Gợi ý: </font><br>");
        question.append(suggest);

        return question.toString();
    }
}
