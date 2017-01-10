/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Object.Subjects;
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
public class SubjectsModel {
    public static Boolean writeSubjects(Subjects subjects) {
        try {
            FileOutputStream file = new FileOutputStream("src/Data/subjects.dat");
            try (ObjectOutputStream oStream = new ObjectOutputStream(file)) {
                oStream.writeObject(subjects);
                oStream.close();
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error write file", "ERROR", 2);
            return false;
        }
    }

    public static Subjects readSubjects() {
        try {
            FileInputStream file = new FileInputStream("src/Data/subjects.dat");
            Subjects subjects;
            try (ObjectInputStream inStream = new ObjectInputStream(file)) {
                subjects = (Subjects) inStream.readObject();
                inStream.close();
                return subjects;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error read file", "ERROR", 2);
            return null;
        }
    }
}
