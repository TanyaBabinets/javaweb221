
package itstep.learning.services.db;

import com.google.gson.Gson;
import itstep.learning.rest.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RestService {
    private final Gson gson=new Gson();
    
    public void sendResponse(HttpServletResponse resp, RestResponse restResponse) throws IOException {
        resp.setContentType("application/json");
        setCorsHeaders(resp);
        resp.getWriter().print(gson.toJson(restResponse));
}
    public void setCorsHeaders(HttpServletResponse resp){
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            resp.setHeader("Access-Control-Allow-Headers", "content-type, authorization");
    }
    
    public <T> T fromJson(String json, Class<T> classOfT){
    return gson.fromJson(json, classOfT);
    }
}
