
package itstep.learning.rest;


public class TimeResponse {
     private long timestamp;
        private String iso;
        
       public TimeResponse(long timestamp, String iso) {
            this.timestamp = timestamp;
            this.iso = iso;
        }
    }
