/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Object.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Duc Dung Dan
 */
public class UserModel {
    public ArrayList<User> users = new ArrayList<>();
    
    public Boolean SignUp(User user, String password) {
        try {
            FileOutputStream file = new FileOutputStream("src/Data/users.dat");
            try (ObjectOutputStream oStream = new ObjectOutputStream(file)) {
                for (int i = 0; i < users.size(); ++i) {
                    if (users.get(i).getEmail().equals(user.getEmail())) {
                        JOptionPane.showMessageDialog(null, "Đã có người sử dụng email này", "Lỗi", 2);
                        return false;
                    }
                }
                user.setAuth(HMAC_SHA256(user.getEmail(), password));
                users.add(user);
                oStream.writeObject(users);
                oStream.close();
                return true;

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error write file", "ERROR", 2);
            return false;
        }
    }

    public User LogIn(String email, String password) {
        try {
            String auth = HMAC_SHA256(email, password);
            FileInputStream file = new FileInputStream("src/Data/users.dat");
            User user;
            try (ObjectInputStream inStream = new ObjectInputStream(file)) {
                users = (ArrayList<User>) inStream.readObject();
                for(int i = 0; i < users.size(); ++i) {
                    if(users.get(i).getEmail().equals(email) && users.get(i).getAuth().equals(auth)) {
                        inStream.close();
                        return users.get(i);
                    }
                }
                
                inStream.close();
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error read file", "ERROR", 2);
            return null;
        }
    }
    
    public static String HMAC_SHA256(String email, String password) {
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(password.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String auth = Base64.encodeBase64String(sha256_HMAC.doFinal(email.getBytes()));
            return auth;
        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            System.out.println("Error HS-256");
            return "";
        }
    }
}
