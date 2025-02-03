
 
package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.cj.jdbc.MysqlDataSource;
import itstep.learning.models.UserSignUpFormModel;
import itstep.learning.rest.RestResponse;
import itstep.learning.servicerandom.DateTimeService;
import itstep.learning.servicerandom.RandomService;
import itstep.learning.servicerandom.UtilRandomService;
import jakarta.jms.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
private final RandomService randomService;
private final DateTimeService datetimeservice;

@Inject
    public HomeServlet(RandomService randomService, DateTimeService datetimeService) {
        this.randomService = randomService;
        this.datetimeservice=datetimeService;
    }
    
    
    private final Gson gson=new Gson();

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String mes="OK";
        String message;
        
        try {
            DriverManager.registerDriver(
                    new com.mysql.cj.jdbc.Driver()
            );   
            String connectionString="jdbc:mysql://localhost:3306/java221";
            java.sql.Connection connection=DriverManager.getConnection( connectionString, "user221", "pass221" );
        
//1            String sql="SELECT CURRENT_TIMESTAMP";
            String sql="SHOW DATABASES";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery( sql );
            List<String> databases = new ArrayList<>();
            while(resultSet.next()){
            databases.add(resultSet.getString(1));
            }
            message=String.join(", ", databases);
// 2           resultSet.next();
// 3           message= resultSet.getString(1);///JDBC count from 1
            ///
            
            
           
        } catch (SQLException ex) {
            message=ex.getMessage();
        }
        
//        try{
//        MysqlDataSource mds = new MysqlDataSource();
//        mds.setURL("jdbc:mysql://localhost:3306/java221");
//        java.sql.Connection connection = mds.getConnection( "user221", "pass221" );
//        } catch (SQLException ex) {
//           message=ex.getMessage();
//        }
        

String timestamp=datetimeservice.getTimestamp();

        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5174");
        resp.getWriter().print(gson.toJson
        (new RestResponse().setStatus(200).setMessage(message+randomService.randomInt()+"  TIME: "+timestamp)));
       
    }

    
   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = new String(req.getInputStream().readAllBytes());
//        UserSignUpFormModel model= gson.fromJson(body, UserSignUpFormModel.class);
        UserSignUpFormModel model;
        RestResponse restResponse=
                new RestResponse()
                .setResourceUrl("POST /home");
//                .setCacheTime(0);
        
        
        try{
        model=gson.fromJson(body, UserSignUpFormModel.class);
        }
        catch(Exception ex){
        sendJson(resp, restResponse.setStatus(422)
        .setMessage(ex.getMessage())
        );
        return;
        }
        
        sendJson(resp, new RestResponse()
        .setResourceUrl("POST /home")
        .setStatus(201)
        .setMessage("Created")
      //  .setCacheTime(0)
        .setMeta(Map.of(
        "dataTyoe", "object",
                "read", "GET /home",
                "update", "PUT /home",
                "delete", "DELETE /home"
        ))
                .setData(model)
        );
//        sendJson(resp, 201,body);
    }
    
    
    
private void sendJson(HttpServletResponse resp, RestResponse restResponse) throws IOException{
  resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5174");
        resp.getWriter().print(gson.toJson(restResponse));
//        (gson.toJson(new RestResponse().setStatus(status).setMessage(message)));
}


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5174");
          resp.setHeader("Access-Control-Allow-Headers", "content-type");
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