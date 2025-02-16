
package itstep.learning.services.db;

import com.google.inject.Singleton;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class MySqlDbService implements DbService{
private Connection connection;
    @Override
    public Connection getConnection() throws SQLException {
        if(connection==null){
//          DriverManager.registerDriver(
//                    new com.mysql.cj.jdbc.Driver()
//            );
//            String connectionString = "jdbc:mysql://localhost:3306/java221";
//            java.sql.Connection connection = DriverManager.getConnection(connectionString, "user221", "pass221");
  MysqlDataSource mds = new MysqlDataSource();
       mds.setURL("jdbc:mysql://localhost:3306/java221");
        connection = mds.getConnection( "user221", "pass221" );
        }
        
        return connection;
    }
    
}
