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
public class MultipleChoice extends Question implements Serializable{
    private ArrayList<Answer> answers = new ArrayList<>();

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    
    public Answer getAnswer(int i) {
        if (i >= 0 && i < this.answers.size()) {
            return answers.get(i);
        } else {
            return answers.get(0);
        }
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
    
    public void setAnswer(Answer answer) {
        this.answers.add(answer);
    }
    
    public void removeAns(int i) {
        if(i >= 0 && i < this.answers.size()) {
            this.answers.remove(i);
        }
    }
    
    public void updateAnswer(int i, Answer answer) {
        if (i >= 0 && i < this.answers.size()) {
            this.answers.set(i, answer);
        }
    }

    @Override
    public String printfQuestion() {
        StringBuilder question = new StringBuilder();
        question.append("Chương - ").append(super.getChapter()).append("<br>");
        question.append("Độ khó: ").append(super.getLevel()).append("<br>");
        
        question.append("Đề: ").append(super.getContentQuestion()).append("<br><br>Đáp án: <br>");
        for(int i = 0; i < answers.size(); ++i) {
            if (answers.get(i).getAccuracy()) {
                question.append("<font color='red'>").append((char) (65 + i)).append(". ").append(answers.get(i).getContentAnswer()).append("</font>");
            } else {
                question.append((char) (65 + i)).append(". ").append(answers.get(i).getContentAnswer());
            }
            question.append("<br>");
        }
        question.append("</html>");
        return question.toString();
    }
    
}
