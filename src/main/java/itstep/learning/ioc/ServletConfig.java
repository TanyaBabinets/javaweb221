
package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.*;
import itstep.learning.servlets.HomeServlet;
import itstep.learning.servlets.RandomServlet;
import itstep.learning.servlets.ProductServlet;
import itstep.learning.servlets.StorageServlet;
import itstep.learning.servlets.TimeServlet;
import itstep.learning.servlets.UserServlet;


public class ServletConfig extends ServletModule{

    @Override
    protected void configureServlets() {
        filter("/*").through(CharsetFilter.class);//регистрация фильтра - все запросы * будут проходить через фильтр
        //можно добавить перед переходо до сервлетов
      //  filter("/*").through(AuthFilter.class);
         filter("/*").through(AuthJwtFilter.class);
        
        //заменяем во всем проекте @WebServlet
        //на @Singleton
        serve("/home").with(HomeServlet.class);
         serve("/user").with(UserServlet.class);
         serve("/time").with(TimeServlet.class);
           serve("/random").with(RandomServlet.class);
         serve("/product").with(ProductServlet.class);
         serve("/storage/*").with(StorageServlet.class);
    }
    
    
}
