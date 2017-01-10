/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Object.User;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc Dung Dan
 */
public class UserController {
    public static String[] listCity = {"An Giang", "Bà Rịa - Vũng Tàu", "Bạc Liêu", "Bắc Kạn", "Bắc Giang", "Bắc Ninh", "Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Cần Thơ", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Đồng Nai", "Đồng Tháp", "Điện Biên", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hòa Bình", "Hậu Giang", "Hưng Yên", "TP Hồ Chí Minh", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lào Cai", "Lạng Sơn", "Lâm Đồng", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên - Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái"};

    public static Models.UserModel userModel = new Models.UserModel();
    public static User user;
    
    public static Boolean LogIn(String email, String password) {
        if(email.length() == 0 || password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập Email hoặc password", "Thông báo", 1);
            return false;
        } else {
            user = userModel.LogIn(email, password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu", "Thông báo", 3);
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Xin chào " + user.getName(), "Thông báo", 3);
                return true;
            }
        }
    }
    
    public static Boolean SignUp(User newUser, String password) {
        if(newUser.getEmail().length() == 0 || password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập  email và mật khẩu", "Thông báo", 3);
            return false;
        } else {
            if (userModel.SignUp(newUser, password)) {
                JOptionPane.showMessageDialog(null, "Đăng ký thành công", "Thông báo", 3);
                return true;
            } else {
                return false;
            }
        }
    }

    public static void viewTableUsers(JTable tableUsers) {
        DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < userModel.users.size(); ++i) {
            model.addRow(new Object[]{i+1, userModel.users.get(i).getEmail(), userModel.users.get(i).getName(), userModel.users.get(i).getSex()? "Nam": "Nữ", ft.format(userModel.users.get(i).getBirth()), userModel.users.get(i).getAdress(), userModel.users.get(i).getCity()});
        }
    }
}
