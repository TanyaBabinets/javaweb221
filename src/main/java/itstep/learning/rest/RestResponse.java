
package itstep.learning.rest;

import itstep.learning.models.UserSignUpFormModel;
import java.util.Map;


public class RestResponse {
    private int status;
    private String message;
private String resourseUrl;

    public String getResourseUrl() {
        return resourseUrl;
    }

    public void setResourseUrl(String resourseUrl) {
        this.resourseUrl = resourseUrl;
    }
    private String resourceUrl;///   
    private Map<String, String>meta;
    private long cacheTime;//seconds
    private Object data;

    public Object getData() {
        return data;
    }

    public RestResponse setData(Object data) {
        this.data = data;
         return this;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public RestResponse setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
         return this;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public RestResponse setMeta(Map<String, String> meta) {
        this.meta = meta;
         return this;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public RestResponse setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
         return this;
    }

    public int getStatus() {
        return status;
    }

    public RestResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    
    
    
}
