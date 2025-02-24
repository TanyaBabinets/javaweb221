
package itstep.learning.services.random;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.time.Instant;
import java.util.Random;



@Singleton

public class UtilRandomService implements RandomService {
    private static final String strName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String fileName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
private Random random = new Random();

@Inject
    public UtilRandomService(DateTimeService datetimeservice) {
        long seed=Instant.parse(datetimeservice.getTimestamp()).toEpochMilli(); 
        this.random=new Random(seed);
    }
    
    @Override
    public int randomInt() {
        return random.nextInt();
    }

    @Override
    public String randomStr(int length) {
      StringBuilder rs=new StringBuilder(length);
      for(int i=0; i<length; i++){
      int index=random.nextInt(strName.length());
      rs.append(strName.charAt(index));
      }
      return rs.toString();
    }

    @Override
    public String randomFile(int length) {
       StringBuilder rf=new StringBuilder(length);
         for(int i=0; i<length; i++){
       int index=random.nextInt(fileName.length());
       rf.append(fileName.charAt(index));
    }
    return rf.toString();
    }
    
}
