package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import itstep.learning.services.db.DbService;
import itstep.learning.services.db.MySqlDbService;
import itstep.learning.services.config.ConfigService;
import itstep.learning.services.config.JsonConfigService;


import itstep.learning.services.random.RandomService;
import itstep.learning.services.kdf.KdfService;
import itstep.learning.services.kdf.PbKdf1Service;
import itstep.learning.services.hash.HashService;
import itstep.learning.services.hash.MD5HashService;
import itstep.learning.services.hash.storage.DiskStorageService;
import itstep.learning.services.hash.storage.StorageService;
import itstep.learning.services.random.DateTimeService;
import itstep.learning.services.random.UtilRandomService;
import itstep.learning.services.form_parse.FormParseService;
import itstep.learning.services.form_parse.MixedFormParseService;

@Singleton
public class ServiceConfig extends AbstractModule {

    @Override
    protected void configure() {
          //  bind(RandomService.class).to(UtilRandomService.class);
        bind(DateTimeService.class).toInstance(new DateTimeService());
       bind(KdfService.class).to(PbKdf1Service.class);
        bind(HashService.class).to(MD5HashService.class);
       bind(RandomService.class).to(UtilRandomService.class);
        bind(DbService.class).to(MySqlDbService.class);
        bind(ConfigService.class).to(JsonConfigService.class);
        bind( FormParseService.class ).to( MixedFormParseService.class );
        bind( StorageService.class ).to( DiskStorageService.class );
        bind(ConfigService.class).to(JsonConfigService.class);
     
        // toInstance создает jwt вручную, а не динамически через inject и получает обьект configservice 
        
        //ця інструкція аналогічна AddSingleton<IRandomService, UtilRandomService>() 
    }

}
