
package itstep.learning.dal.dao;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import java.sql.SQLException;


@Singleton
public class DataContext {
 //  private final Connection connection;
    private final UserDao userDao;
      private final AccessTokenDao accessTokenDao;
  //   private final Logger logger;
   //  private final Injector injector;

@Inject
    public DataContext(Injector injector) throws SQLException {
     //   this.connection = dbservice.getConnection();
      //  userDao=new UserDao(connection, logger);
    //    this.logger = logger;
     //   this.injector = injector;
        userDao=injector.getInstance(UserDao.class);
        accessTokenDao=injector.getInstance(AccessTokenDao.class);
    }

    public AccessTokenDao getAccessTokenDao() {
        return accessTokenDao;
    }
      public UserDao getUserDao() {
        return userDao;
    }
    
    
}
