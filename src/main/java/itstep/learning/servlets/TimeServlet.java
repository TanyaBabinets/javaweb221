
package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.rest.TimeResponse;
import itstep.learning.services.random.UtilRandomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Singleton
public class TimeServlet extends HttpServlet{
     private final Gson gson=new Gson();

     
     @Inject 
     private UtilRandomService seedRS;
     
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
       // resp.getWriter().print(gson.toJson(new RestResponse().setStatus(200).setMessage("OK")));
 
        Instant now = Instant.now();//time now
        long timestamp = now.toEpochMilli();
        String iso = now.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
       int randomNumber = seedRS.randomInt();
        TimeResponse timeResponse = new TimeResponse(timestamp, iso, randomNumber);
              String jsonResponse = gson.toJson(timeResponse);

        resp.getWriter().write(jsonResponse);

    }
}


