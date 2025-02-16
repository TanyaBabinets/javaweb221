
package itstep.learning.dal.dao;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dto.User;
import itstep.learning.models.UserSignUpFormModel;
import itstep.learning.services.db.DbService;
import itstep.learning.services.kdf.KdfService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class UserDao {
 private final Connection connection;
 private final Logger logger;
 private final KdfService kdfService;
  private final DbService dbService;
 
@Inject
    public UserDao(DbService dbService, java.util.logging.Logger logger, itstep.learning.services.kdf.KdfService kdfService, itstep.learning.services.db.DbService dbService1) throws SQLException {
     this.connection = dbService.getConnection();
     this.logger = logger;
     this.kdfService = kdfService;
     this.dbService = dbService;
    }
   
    
    
    public User addUser(UserSignUpFormModel userModel){
        User user=new User();
   user.setUserId(UUID.randomUUID());
   user.setName(userModel.getName());
   user.setEmail(userModel.getEmail());
                           
        String sql="INSERT INTO users (user_id, name, email, phone, reg_date, city)"
                +" VALUES(?,?,?,?,?,?)";
        try(PreparedStatement prep=this.connection.prepareStatement(sql)){
            prep.setString(1, user.getUserId().toString());
             prep.setString(2, user.getName());
              prep.setString(3, user.getEmail());
               prep.setString(4, user.getPhone());  
                  prep.setTimestamp(5, new java.sql.Timestamp(user.getRegdate().getTime()));
                prep.setString(6, user.getCity());
            this.connection.setAutoCommit(false);
        prep.executeUpdate();
      // dbService
        this.connection.commit();
        }
        catch (SQLException ex){
logger.warning("UserDao:installUsers "+ ex.getMessage());
try{
this.connection.rollback();
}
catch(SQLException ignore){}
return null;
}
        
        sql="INSERT INTO users_access (user_access_id, user_id, role_id, login, salt, dk)"
                +" VALUES(UUID(), ?,'guest', ?,?,?)";
        try(PreparedStatement prep=this.connection.prepareStatement(sql)){
            prep.setString(1, user.getUserId().toString());
             prep.setString(2, user.getEmail());
             String salt=UUID.randomUUID().toString().substring(0,16);
              prep.setString(3, salt);
               prep.setString(4, kdfService.dk(userModel.getPassword(), salt));
        prep.executeUpdate();
        }
        catch (SQLException ex){
logger.warning("UserDao:installUsers "+ ex.getMessage());
return null;
        }
    return user;
    }
    
    
    public User authorize (String login, String password){
    String sql="SELECT * FROM users_access ua " +
"JOIN users u ON ua.user_id=u.user_id " +
"WHERE ua.login=?";
    
    try
        (PreparedStatement prep=dbService.getConnection().prepareStatement(sql)){
    prep.setString(1, login);
    ResultSet rs=prep.executeQuery();
    if(rs.next()){
    String dk=kdfService.dk(password, rs.getString("salt"));
    if (Objects.equals(dk, rs.getString("dk"))){
    return //фабрика паттерн и тут 2 варианта:
           // new User(rs); 1st variant
            User.fromResultSet(rs);
        }
    }
    }
     catch (SQLException ex){
logger.log(Level.WARNING, "UserDao:Authorize {0}", ex.getMessage());
}
    return null;
    }
    
    public boolean installTables() {

return installUserAccess()&&installUsers();
    }
    
    
    private boolean installUsers(){

   String sql = "CREATE TABLE IF NOT EXISTS users("
        + "user_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),"
        + "name VARCHAR(128) NOT NULL,"
        + "email VARCHAR(256) NULL,"
          + "phone VARCHAR(32) NULL,"
                     + "reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,"
             + "city VARCHAR(32) NULL"
        + ") Engine=InnoDB, DEFAULT CHARSET=utf8mb4";

try(Statement statement=connection.createStatement()){
statement.executeUpdate(sql);
logger.info("UserInstall OK");
return true;
}
catch (SQLException ex){
logger.warning("UserDao:installUsers "+ ex.getMessage());
}
return false;
    }
    public boolean installUserAccess(){

   String sql = "CREATE TABLE IF NOT EXISTS users_access("
        + "user_access_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),"
              + "user_id CHAR(36) NOT NULL,"
               + "role_id VARCHAR(16) NOT NULL,"
        + "login VARCHAR(128) NOT NULL,"
        + "salt CHAR(16) NOT NULL,"
          + "dk CHAR(20) NOT NULL,"
             + "UNIQUE(login)"
        + ") Engine=InnoDB, DEFAULT CHARSET=utf8mb4";

try(Statement statement=connection.createStatement()){
statement.executeUpdate(sql);
logger.info("UserInstallAccess OK");
return true;
}
catch (SQLException ex){
logger.warning("UserDao:installUserAccess "+ ex.getMessage());
}
return false;
    
    }
}
