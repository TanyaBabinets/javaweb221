package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.DataContext;
import itstep.learning.dal.dto.AccessToken;
import itstep.learning.dal.dto.User;
import itstep.learning.dal.dto.UserAccess;
import itstep.learning.models.UserAuthJwtModel;
import itstep.learning.models.UserSignUpFormModel;
import itstep.learning.rest.RestResponse;
import itstep.learning.services.db.RestService;
import itstep.learning.services.hash.HashService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Inject
    public UserServlet(itstep.learning.dal.dao.DataContext datacontext, Logger logger, HashService hashService, itstep.learning.services.db.RestService restService) {
        this.datacontext = datacontext;
        this.restService = restService;
        this.logger = logger;
        this.hashService = hashService;
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
        AccessToken token = datacontext.getAccessTokenDao().create(userAccess);
        User user = datacontext.getUserDao().getUserById(userAccess.getUserId());

        String jwtHeader = new String(Base64.getUrlEncoder().encode(
                "{\"alg\": \"HS256\", \"typ\": \"JWT\" }".getBytes()));
        String jwtPayload = new String(Base64.getUrlEncoder().encode(
                restService.gson.toJson(userAccess).getBytes()));
        String jwtSignature = new String(Base64.getUrlEncoder().encode(
                hashService.digest("the secret" + jwtHeader + "." + jwtPayload).getBytes()));
        String jwtToken = jwtHeader + "." + jwtPayload + "." + jwtSignature;

        restResponse.setStatus(200)
                //.setData(new UserAuthViewModel (user, userAccess, token) списать сюда////2:15 28.02 списать
                .setData(new UserAuthJwtModel(user, jwtToken))
                .setCacheTime(600);

        restService.sendResponse(resp, restResponse);
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
