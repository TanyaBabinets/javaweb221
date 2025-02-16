
package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.CharsetFilter;
import itstep.learning.servlets.HomeServlet;
import itstep.learning.servlets.UserServlet;


public class ServletConfig extends ServletModule{

    @Override
    protected void configureServlets() {
        filter("/*").through(CharsetFilter.class);//регистрация фильтра - все запросы * будут проходить через фильтр
        //можно добавить перед переходо до сервлетов
        
        
        //заменяем во всем проекте @WebServlet
        //на @Singleton
        serve("/home").with(HomeServlet.class);
         serve("/user").with(UserServlet.class);
    }
    
    
}
