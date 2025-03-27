package itstep.learning.servlets;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.DataContext;
import itstep.learning.dal.dto.AccessToken;
import itstep.learning.dal.dto.User;
import itstep.learning.dal.dto.UserAccess;
import itstep.learning.models.UserAuthJwtModel;
import itstep.learning.rest.RestResponse;
import itstep.learning.services.config.ConfigService;
import itstep.learning.services.db.RestService;
import itstep.learning.services.hash.HashService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class UserServlet extends HttpServlet {

    private final DataContext datacontext; 
    private final RestService restService;
    private final Logger logger;
    private final HashService hashService;
private final ConfigService configService;

    @Inject
    public UserServlet(DataContext datacontext, RestService restService, Logger logger, HashService hashService, ConfigService configService) {
        this.datacontext = datacontext;
        this.restService = restService;
        this.logger = logger;
        this.hashService = hashService;
        this.configService = configService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestResponse restResponse
                = new RestResponse()
                        .setResourceUrl("GET /user")
                        .setCacheTime(600)
                        .setMeta(Map.of(
                                "dataType", "object",
                                "read", "GET /user",
                                "update", "PUT /user",
                                "delete", "DELETE /user"
                        ));
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            restService.sendResponse(resp,
                    restResponse.setStatus(401)
                            .setData("Authorization header required"));
            return;
        }
        String authScheme = "Basic ";
        if (!authHeader.startsWith(authScheme)) {
            restService.sendResponse(resp,
                    restResponse.setStatus(422)
                            .setData("Authorization scheme error"));
            return;
        }
        String credentials = authHeader.substring(authScheme.length());
        try {
            credentials = new String(
                    Base64.getDecoder().decode(
                            credentials.getBytes()));
        } catch (Exception ex) {
            restService.sendResponse(resp,
                    restResponse.setStatus(422)
                            .setData("Decode Error" + ex.getMessage()));
            return;
        }
//                    }
        String[] parts = credentials.split(":", 2);
        if (parts.length != 2) {
            restService.sendResponse(resp,
                    restResponse.setStatus(422)
                            .setData("FormatError splitting by ':'"));
            return;
        }
        UserAccess userAccess = datacontext.getUserDao().authorize(parts[0], parts[1]);
        if (userAccess == null) {
            restService.sendResponse(resp,
                    restResponse.setStatus(401)
                            .setData("Credentials rejected"));
            return;
        }
//создаем токен для пользователя
//ПОЛУчить секретный ключ из конфигурации
String secret=configService.getValue("jwt.secret").getAsString();

        AccessToken token = datacontext.getAccessTokenDao().create(userAccess);
        User user = datacontext.getUserDao().getUserById(userAccess.getUserId());
//28.02 2;00

long now=System.currentTimeMillis()/1000;//time in sec
long expAt=now + (configService.getValue("token.lifetime").getAsInt());//srok deystviua
        String jwtHeader = new String(Base64.getUrlEncoder().encode(
                "{\"alg\": \"HS256\", \"typ\": \"JWT\" }".getBytes()));
   //     String jwtPayload=restService.gson.toJson(userAccess);
       JsonObject payloadJson=restService.gson.toJsonTree(userAccess).getAsJsonObject();//Tree чтоб добавить новое свойство expAt
       payloadJson.addProperty("exp", expAt);//получаем все данные юзера из обьекта
       // Добавляем iat (время создания) и exp (время истечения) в payload
payloadJson.addProperty("iat", now);
payloadJson.addProperty("exp", expAt);
       
      //  jwtPayload = new String(Base64.getUrlEncoder().encode(        
                // restService.gson.toJson(userAccess).getBytes()));
                String jwtPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(
                restService.gson.toJson(payloadJson).getBytes(StandardCharsets.UTF_8));
        
       
                String jwtSignature = new String(Base64.getUrlEncoder().encode(
    hashService.digest(secret + jwtHeader + "." + jwtPayload).getBytes()));
                
                
                String jwtToken = jwtHeader + "." + jwtPayload + "." + jwtSignature;

  
     
       restResponse
                .setStatus( 200 )
                .setData( 
                        new UserAuthJwtModel( user, jwtToken )
                )
                .setCacheTime( 600 );
        restService.sendResponse( resp, restResponse );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestResponse restResponse
                = new RestResponse()
                        .setResourceUrl("PUT /user")
                        .setMeta(Map.of(
                                "dataType", "object",
                                "read", "GET /user",
                                "update", "PUT /user",
                                "delete", "DELETE /user"
                        ));
        //checking авторизацию по токену
        UserAccess userAccess = (UserAccess) req.getAttribute("AuthUserRequest");
        ////перенести отсюда
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            restService.sendResponse(resp,
                    restResponse.setStatus(401)
                            .setData("Authorization header required"));
            return;
        }
        String authScheme = "Bearer ";
        if (!authHeader.startsWith(authScheme)) {
            restService.sendResponse(resp,
                    restResponse.setStatus(422)
                            .setData("Authorization scheme error"));
            return;
        }
        String credentials = authHeader.substring(authScheme.length());
        credentials = new String(Base64.getDecoder().decode(credentials.getBytes()));
        restResponse.setData(credentials);
        restService.sendResponse(resp, restResponse);
        ///все перенесли в AuthFilter
        //     UserAccess userAccess = datacontext.getAccessTokenDao().getUserAccess( credentials );
        if (userAccess == null) {
            restService.sendResponse(resp,
                    restResponse
                            .setStatus(401)
                            .setData(req.getAttribute("authStatus")));
            return;
        }
        
        User userUpdates;
        try {
            userUpdates = restService.fromBody(req, User.class);
        } catch (IOException ex) {
            restService.sendResponse(resp, restResponse
                    .setStatus(422)
                    .setData("Decode error: " + ex.getMessage()));
//                    .setMessage( ex.getMessage()+"" ) 
            // );
            return;
        }

        String[] parts = credentials.split(":", 2);
        if (parts.length != 2) {
            restService.sendResponse(resp,
                    restResponse.setStatus(422)
                            .setData("Format error spliting by ':'"));
            return;

        }
        restResponse.setStatus(200).setData(userUpdates);
        restService.sendResponse(resp, restResponse);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestResponse restResponse
                = new RestResponse()
                        .setResourceUrl("DELETE /user")
                        .setMeta(Map.of(
                                "dataType", "object",
                                "read", "GET /user",
                                "update", "PUT /user",
                                "delete", "DELETE /user"
                        ));
        String userId = req.getParameter("id");//  /user?id=...
        if (userId == null) {
            restService.sendResponse(resp, restResponse
                    .setStatus(400)
                    .setData("Missing requried ID"));
            return;
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userId);
        } catch (Exception ignore) {
            restService.sendResponse(resp, restResponse
                    .setStatus(400)
                    .setData("Invalid ID format"));
            return;
        }
        User user = datacontext.getUserDao().getUserById(userUuid);
        if (user == null) {
            restService.sendResponse(resp, restResponse
                    .setStatus(401)
                    .setData("UnAutorized"));
            return;
        }

        try {
            datacontext.getUserDao().deleteAsync(user).get();
        } catch (InterruptedException | ExecutionException ex) {
            logger.log(Level.SEVERE, "deleteAsync fail: {0}", ex.getMessage());
            restService.sendResponse(resp, restResponse
                    .setStatus(500)
                    .setData("Server erro.See server's logs"));
            return;
        }
        restResponse
                .setStatus(202)
                .setData("Deleted")
                .setCacheTime(0);
        restService.sendResponse(resp, restResponse);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }
}
