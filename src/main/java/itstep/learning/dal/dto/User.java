
package itstep.learning.dal.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class User {
      private UUID userId;
      private String name;
      private String email;
      private String phone;
      private Date regdate;
      private String city;
      
      public static User fromResultSet(ResultSet rs) throws SQLException{
      User user=new User();
      user.setUserId(UUID.fromString(rs.getString("user_id")));
      user.setName(rs.getString("name"));
       user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
      user.setRegdate(new java.util.Date(rs.getTimestamp("reg_date").getTime()));
            user.setCity(rs.getString("city"));
          return user;
      }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
      
}
