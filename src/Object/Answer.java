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
public class Answer implements Serializable{
    private String contentAnswer;
    private Boolean accuracy;


    public String getContentAnswer() {
        return contentAnswer;
    }

    public Boolean getAccuracy() {
        return accuracy;
    }

    public void setContentAnswer(String contentAnswer) {
        if (contentAnswer.trim().length() != 0) {
            this.contentAnswer = contentAnswer.trim();
        }
    }

    public void setAccuracy(Boolean accuracy) {
        this.accuracy = accuracy;
    }
}
