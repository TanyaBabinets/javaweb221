package itstep.learning.servlets;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.services.random.RandomService;
import jakarta.json.Json;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Singleton
public class RandomServlet extends HttpServlet {

    @Inject
    RandomService randomService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String type = req.getParameter("type");
        String length = req.getParameter("length");
        int length1 = 10;

        if (length != null) {
            try {
                length1 = Integer.parseInt(length);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid length parameter");
                return;
            }
        }
       

if (type == null || type.isEmpty()) {
    type = "default";
}
 String randomString = randomService.generateRandomString(type, length1);
//String randomStr = randomService.generateRandomString(type, 10);
        // Формируем ответ
//        "status": 200,
//  "resourceUrl": "GET /random",
//  "meta": {
//    "dataType": "string",
//    "read": "GET /random",
//    "type": "salt",
//    "length": 10
//  },
//  "cacheTime": 0,
//  "data": "8s+3/.v`0)"
        jakarta.json.JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", 200)
                .add("resourceUrl", "GET /random")
                .add("meta", Json.createObjectBuilder()
                        .add("dataType", "string")
                        .add("read", "GET /random")
                        .add("type", type)
                        .add("length", length1))
                .add("cacheTime", 0)
                .add("data", randomString)
                .build();

        // Отправляем ответ
        resp.getWriter().write(jsonResponse.toString());
    }
}

