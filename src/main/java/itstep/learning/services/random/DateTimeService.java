
package itstep.learning.services.random;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeService {
    public String getTimestamp() {
        Instant now = Instant.now();//time now
     return now.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
}
}
