
package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.servlets.HomeServlet;


public class ServletConfig extends ServletModule{

    @Override
    protected void configureServlets() {
        //заменяем во всем проекте @WebServlet
        //на @Singleton
        serve("/home").with(HomeServlet.class);
        
    }
    
    
}
