
package itstep.learning.filters;

import com.google.inject.Singleton;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

@Singleton
public class CharsetFilter implements Filter {

    private FilterConfig filterConfig;

     @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
        
    }
//инит - работает, когда создается обьект Фильтр    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next) throws IOException, ServletException {
       //типа Invoke
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        next.doFilter(req, resp);
    } 
    @Override
    public void destroy() {
       this.filterConfig=null;
    }   
}
/*
Фильтры - сервлетные фильтры - 
то классы, что играют роль middleware
запросы сначала проходят фильтры, потом распределяются на сервлеты.
(контроллеры)


*/