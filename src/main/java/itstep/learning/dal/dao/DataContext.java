
package itstep.learning.dal.dao;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import itstep.learning.services.db.DbService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Singleton
public class DataContext {
   private final Connection connection;
    private final UserDao userDao;
     private final Logger logger;
     private final Injector injector;

    public Connection getConnection() {
        return connection;
    }

    public UserDao getUserDao() {
        return userDao;
    }
@Inject
    public DataContext(DbService dbservice, java.util.logging.Logger logger, Injector injector) throws SQLException {
        this.connection = dbservice.getConnection();
      //  userDao=new UserDao(connection, logger);
        this.logger = logger;
        this.injector = injector;
        userDao=injector.getInstance(UserDao.class);
    }
    
    
    
}
