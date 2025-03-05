
package itstep.learning.services.random;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.annotation.PreDestroy;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;



@Singleton

public class UtilRandomService implements RandomService {
    private static final String strName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String fileName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
private Random random = new Random();
private final SecureRandom secureRandom = new SecureRandom();//добавляем для генерирования соли
  @PreDestroy
    public void shutdown() {
        System.out.println("shutting down");
       
    }


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

    @Override
    public String generateRandomString(String type, int length) {
         if (length <= 0) {
            throw new IllegalArgumentException("Length must be more than 0");
        }
         if (type == null) {
        type = "default"; 
    }
          switch (type) {
            case "salt":
                return Base64.getEncoder().encodeToString(secureRandom.generateSeed(length)).substring(0, length);
            case "file":
                return randomFile(length);
            case "default":
            default:
                return randomStr(5);
        }       
    }
}
