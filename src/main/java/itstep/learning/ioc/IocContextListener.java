
package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;


public class IocContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
           return Guice.createInjector( //builder services ASP
    new ServletConfig(),
    new ServiceConfig()
    );
    }

}

/*
Context Listener - слухаи подій створення контексту, тобто
запуску проекту. Можуть вважатися точкою входу/запуску
*/