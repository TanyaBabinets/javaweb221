
package itstep.learning.models;


import java.math.BigDecimal;
import java.util.Date;

public class UserSignUpFormModel {
    private String name;
    private String login;
private String email;
     private String phone;
      private Date regdate;
       private String password;
       private int age;
        private BigDecimal balance;
           private Date deleteMoment;


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getDeleteMoment() {
        return deleteMoment;
    }

    public void setDeleteMoment(Date deleteMoment) {
        this.deleteMoment = deleteMoment;
    }
        
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
       private String city;
       
       
       
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
     

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
