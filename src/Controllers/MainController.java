/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Object.User;
import Views.Login;
import Views.ExamViewing;
import Views.Home;
import Views.Main;
import Views.Question;
import Views.Subjects;
import Views.Users;
import java.awt.GridLayout;
import javax.swing.JPanel;
/**
 *
 * @author Duc Dung Dan
 */
public class MainController {

    public static void logout(Main aThis) {
        aThis.dispose();
        UserController.user = new User();
        Login login = new Login();
        login.setVisible(true);
    }
    public void startApplication() {
        // View the application's GUI
        Login login = new Login();
        login.setVisible(true);
    }
    
    public static void question(JPanel jpanel) {
        Question question = new Question();
        jpanel.removeAll();
        GridLayout girdlayout = new GridLayout();
        jpanel.setLayout(girdlayout);
        jpanel.add(question.getJPanelQuestion());
        jpanel.updateUI();
        QuestionController.loadTableQuestion(question.tableQuestion);
    }
    
    public static void exam(JPanel jpanel) {
        ExamViewing examViewing = new ExamViewing();
        jpanel.removeAll();
        GridLayout girdlayout = new GridLayout();
        jpanel.setLayout(girdlayout);
        jpanel.add(examViewing.getJPanelExam());
        jpanel.updateUI();
        ExamController.loadTableExam(examViewing.tableExam);
    }
    
    public static void users(JPanel jpanel) {
        Users users = new Users();
        jpanel.removeAll();
        GridLayout girdlayout = new GridLayout();
        jpanel.setLayout(girdlayout);
        jpanel.add(users.background);
        jpanel.updateUI();
    }
    
    public static void home(JPanel jpanel) {
        Home home = new Home();
        jpanel.removeAll();
        GridLayout girdlayout = new GridLayout();
        jpanel.setLayout(girdlayout);
        jpanel.add(home.background);
        jpanel.updateUI();
    }
    
    public static void subjects(JPanel jpanel) {
        Subjects subjects = new Subjects();
        jpanel.removeAll();
        GridLayout girdlayout = new GridLayout();
        jpanel.setLayout(girdlayout);
        jpanel.add(subjects.background);
        jpanel.updateUI();
    }
}
