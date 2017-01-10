/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Duc Dung Dan
 */
public class User implements Serializable {
    private String name;
    private Boolean sex;
    private Date birth;
    private String adress;
    private String city;
    private String email;
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    
    public Boolean getSex() {
        return this.sex;
    }
    
    public void setBirth(Date birth) {
        this.birth = birth;
    }
    
    public Date getBirth() {
        return this.birth;
    }
    
    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    public String getAdress() {
        return this.adress;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
}
