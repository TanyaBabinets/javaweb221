
package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import itstep.learning.servicerandom.RandomService;
import itstep.learning.servicerandom.UtilRandomService;


public class ServiceConfig extends AbstractModule{

    @Override
    protected void configure() {
        bind(RandomService.class).to(UtilRandomService.class);
        //ця інструкція аналогічна AddSingleton<IRandomService, UtilRandomService>() 
    }
    
}
