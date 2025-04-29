package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import static com.mysql.cj.conf.PropertyKey.logger;
import com.mysql.cj.jdbc.MysqlDataSource;
import itstep.learning.dal.dao.DataContext;
import itstep.learning.dal.dto.User;
import itstep.learning.models.UserSignUpFormModel;
import itstep.learning.rest.RestResponse;
import itstep.learning.services.config.ConfigService;
import itstep.learning.services.db.DbService;
import itstep.learning.services.db.RestService;
import itstep.learning.services.random.DateTimeService;
import itstep.learning.services.random.RandomService;
import itstep.learning.services.random.UtilRandomService;
import itstep.learning.services.kdf.KdfService;
import itstep.learning.services.hash.HashService;
import itstep.learning.services.hash.MD5HashService;

import jakarta.jms.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//@WebServlet("/home")

@Singleton
public class HomeServlet extends HttpServlet {
//private final Gson gson=new Gson();

    private final RandomService randomService;
    private final UtilRandomService seedrandomService;
    private final DateTimeService datetimeservice;
    private final KdfService kdfService;
    private final DbService dbService;
    private final DataContext datacontext;
    private final RestService restService;
    private final ConfigService configService;

    @Inject
    public HomeServlet(RandomService randomService, UtilRandomService seedrandomService, DateTimeService datetimeService, KdfService kdfService, DbService dbService, itstep.learning.dal.dao.DataContext datacontext, itstep.learning.services.db.RestService restService, itstep.learning.services.config.ConfigService configService) {
        this.randomService = randomService;
        this.seedrandomService = seedrandomService;
        this.datetimeservice = datetimeService;
        this.kdfService = kdfService;
        this.dbService = dbService;
        this.datacontext = datacontext;
        this.restService = restService;
        this.configService = configService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String message;
        int randomNumber;
        String timestamp;

        try {

            timestamp = datetimeservice.getTimestamp();
            randomNumber = seedrandomService.randomInt();
            String sql = "SELECT CURRENT_TIMESTAMP";
            //          String sql = "SHOW DATABASES";
            Statement statement = dbService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            message = "OK";
// 2           resultSet.next();
// 3           message= resultSet.getString(1);///JDBC count from 1
         ///
            
                 
        } catch (SQLException ex) {
            message = ex.getMessage();
            randomNumber = -1;
            timestamp = "errorTimestamp";
        }

//        try{
//        MysqlDataSource mds = new MysqlDataSource();
//        mds.setURL("jdbc:mysql://localhost:3306/java221");
//        java.sql.Connection connection = mds.getConnection( "user221", "pass221" );
//        } catch (SQLException ex) {
//           message=ex.getMessage();
//        }
        // String timestamp = datetimeservice.getTimestamp();
//int randomNumber=seedrandomService.randomInt();

      
     String msg = datacontext.getUserDao().installTables()
             && datacontext.getAccessTokenDao().installTables()
//               && datacontext.getProductDao().installTables()
         //    && datacontext.getCategoryDao().installTables()
             //  && datacontext.getCategoryDao().seedData()
             ? "Install OK"
             : "Install Fail";
        
        restService.sendResponse( resp, 
                new RestResponse()
                .setStatus( 200 )
                .setMessage( message + " " + 
                        randomService.randomInt() + " " + 
                        configService.getValue("db.MySql.port").getAsInt() + " " + 
                        msg )
        );
    }
//        String msg=
//                 datacontext.getUserDao().installTables()
//                && datacontext.getAccessTokenDao().installTables()
//                && datacontext.getCategoryDao().installTables()
//                 && datacontext.getCategoryDao().seedData()
//                ? "Install OK"
//                : "Install FAIL";
//
//        restService.sendResponse(resp,
//                new RestResponse()
//                        .setStatus(200)
//                        .setMessage(message + " "
//                                + randomService.randomInt() + " "
//                                + configService.getValue("db.MySql.port").getAsInt() + " "
//                                + msg)
        //restService.sendResponse(resp,
        //        new RestResponse()
        //                .setStatus(200)
        //                .setMessage(message="" + " "+msg)
        //        .setData(Map.of(
        //        "random Number: ", randomNumber, "timestamp: ", timestamp, 
        //                "Random String: ", randomService.randomStr(10),
        //                "Random File name: ", randomService.randomFile(6)
        //        ))
        //         .setMessage(message=""+randomService.randomInt()+" "+msg)
//        );
    
//        resp.setContentType("application/json");
//        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//        resp.getWriter().print(gson.toJson(new RestResponse().setStatus(200).setMessage(message +" "+ msg + randomService.randomInt() + " " +"  HASH: "+ kdfService.dk("123", "456") + "  TIME: " + timestamp+ "  RANDOM NUMBER: " + randomNumber)));
//}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String body = new String(req.getInputStream().readAllBytes(),
//        StandardCharsets.UTF_8
//        );
        ////        UserSignUpFormModel model= gson.fromJson(body, UserSignUpFormModel.class);
//        UserSignUpFormModel model;
        RestResponse restResponse
                = new RestResponse()
                        .setResourceUrl("POST /home")
                        .setMeta(Map.of(
                                "dataType", "object",
                                "read", "GET /home",
                                "update", "PUT /home",
                                "delete", "DELETE /home"));
//                .setCacheTime(0);
        UserSignUpFormModel model;
        try {
            model = restService.fromBody(req, UserSignUpFormModel.class);
        } catch (Exception ex) {
            restService.sendResponse(resp, restResponse
                    .setStatus(422)
                    .setMessage(ex.getMessage())
            );
            return;
        }

        User user = datacontext.getUserDao().addUser(model);
        if (user == null) {
            restResponse
                    .setStatus(507)
                    .setMessage("DB Error")
                    .setData(model);
//        sendJson(resp, new RestResponse()
            restService.sendResponse(resp, restResponse);
//                .setResourceUrl("POST /home")
//                .setStatus(507)
//                .setMessage("Created")
            //  .setCacheTime(0)

        } else {
            restResponse
                    .setStatus(201)
                    .setMessage("Created")
                    .setData(model);
            restService.sendResponse(resp, restResponse);
//                sendJson(resp, restResponse);

        }
    }

//    private void sendJson(HttpServletResponse resp, RestResponse restResponse) throws IOException {
//        resp.setContentType("application/json");
//        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//        resp.getWriter().print(gson.toJson(restResponse));
    ////        (gson.toJson(new RestResponse().setStatus(status).setMessage(message)));
//    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }

}

//IoC Inversion of Control - Інверсія управліня - Архітектурний патер, 
//згідно з яким управління життєвим циклом обектів перекладаєтся
//спеціалізований модуль. - його називають контейнер служб, інжектор, resolver.
/*
1.Реєстрація - додавання до контейнера інформаціі, зазвичай у формі (тип - час життя scope)
|тип1  Це називають сервіси - загально доступні обьекти по вьому проекту, автоматично інжектуются, вбудовуюься
тип2
тип2
Container

2/Resolve:class->||->object(у т.ч. не новий обьект)
|Connection|
|Logger|
\injection- передача уже існуючих обьєктів в класс для його внутрішного вжитку - механізм інєкціі
class(Connection c Logger logger)
{ _conn=c, _logger=logger}
(resolver - розбирає контсруктор и видає данні)
DI Dependency injection - спосіб (патерн )передачі посилань на обєети-служби до іших обьєктів

не плутати с DIP Dependency Inversion Principle - SOLID - загально-архітектурний принцип
який радить вживати максимально можливу абстракцію для залежностей

Java, web

Deploy
App -> Tomcat ->Run(event ->listener)коли запускаєтся то застосунок отримує повідомлення 1 раз про запуск
                |  |
       Reqeust1|   |->Response1 already running
       Reqeust2|   |->Response2  new reuqest
               /    \
->[Filters](middlware)->[Servelt](controller)->[JSP](Razor View) (порядок виконання)
  
Guice - бібдіотека Гугл для IoC
Spring 
 */
