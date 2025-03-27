
package itstep.learning.services.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.cj.jdbc.MysqlDataSource;
import itstep.learning.services.config.JsonConfigService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class MySqlDbService implements DbService{

    private static final Logger logger = Logger.getLogger(MySqlDbService.class.getName());
    private final JsonConfigService configService;
private Connection connection;

@Inject
public MySqlDbService(JsonConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Connection getConnection() throws SQLException {
       if (connection==null){
           try{
          String host = configService.get("db.MySql.host");
            int port = configService.getValue("db.MySql.port").getAsInt();
            String dbName = configService.get("db.MySql.schema");
            String user = configService.get("db.MySql.user");
            String password = configService.get("db.MySql.password");
            String params = Optional.ofNullable(configService.get("db.MySql.params"))
                                        .orElse("");
            boolean autoCommit = Boolean.parseBoolean(configService.get("db.MySql.autocommit"));
//String connectionString="jdbc:mysql://localhost:3306/java221"
//        +"?useUnicode=true&characterEncouding=UTF-8";

 String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                    + "?" + params;

//          DriverManager.registerDriver(
//                    new com.mysql.cj.jdbc.Driver()
//            );
//            String connectionString = "jdbc:mysql://localhost:3306/java221";
//            java.sql.Connection connection = DriverManager.getConnection(connectionString, "user221", "pass221");
  MysqlDataSource mds = new MysqlDataSource();
       mds.setURL(connectionString);
     //   connection = mds.getConnection( "user221", "pass221" );
     connection=mds.getConnection(user, password);
      connection.setAutoCommit(autoCommit); 
           }catch(SQLException ex){
            logger.log(Level.SEVERE, "SQL error", ex);//критическая ошибка
            throw new SQLException("fail to connect SQL", ex);
           }
        }
        
        return connection;
    }
    
}
