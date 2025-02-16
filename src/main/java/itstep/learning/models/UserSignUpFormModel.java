
package itstep.learning.models;


import java.util.Date;

public class UserSignUpFormModel {
    private String name;
    private String login;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
     private String email;
     private String phone;
      private Date regdate;
       private String password;
       private String city;

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegdate() {
        return regdate;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }
    
    
}
