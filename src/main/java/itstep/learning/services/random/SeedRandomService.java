
package itstep.learning.services.random;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.time.Instant;
import java.util.Random;

@Singleton
public class SeedRandomService implements RandomService{
private final Random random;

@Inject
    public SeedRandomService(DateTimeService datetimeservice) {
        long seed=Instant.parse(datetimeservice.getTimestamp()).toEpochMilli(); 
        this.random=new Random(seed);
    }
    
    
    @Override
    public int randomInt() {
    return random.nextInt(500);
    }
}
