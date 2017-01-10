/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Object.Exams;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
/**
 *
 * @author Duc Dung Dan
 */
public class ExamModel {

    public static Boolean writeExam(Exams listExam) {
        try {
            FileOutputStream file = new FileOutputStream("src/Data/exams.dat");
            try (ObjectOutputStream oStream = new ObjectOutputStream(file)) {
                oStream.writeObject(listExam);
                oStream.close();
            }
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error write file", "ERROR", 2);
            return false;
        }
    }
    
    public static Exams readExam() {
        try {
            FileInputStream file = new FileInputStream("src/Data/exams.dat");
            Exams listExam;
            try (ObjectInputStream inStream = new ObjectInputStream(file)) {
                listExam = (Exams) inStream.readObject();
                inStream.close();
                return listExam;
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error read file", "ERROR", 2);
            return null;
        }
    }
    
}
