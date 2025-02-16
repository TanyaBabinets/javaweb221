
package itstep.learning.rest;


public class TimeResponse {
     private long timestamp;
        private String iso;
        private int randomNumber;
        
       public TimeResponse(long timestamp, String iso, int randomNumber) {
            this.timestamp = timestamp;
            this.iso = iso;
            this.randomNumber=randomNumber;
        }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
    }
