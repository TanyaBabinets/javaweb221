//
//package itstep.learning.servlets;
//
//import com.google.inject.Inject;
//import com.google.inject.Singleton;
//import itstep.learning.services.db.form_parse.FormParseResult;
//import itstep.learning.services.db.form_parse.FormParseService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import org.apache.commons.fileupload.FileItem;
//
//@Singleton
//public class ProductServlet extends HttpServlet{
//
//    
//    private final FormParseService formParseService;
//@Inject
//    public ProductServlet(FormParseService formParseService) {
//        this.formParseService=formParseService;
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        FormParseResult formParseResult = formParseService.parseRequest(req);
//        
//        String file1=formParcelResult.getFiles().get("file1");
//        String message;
//        if(file1.getSize()>0){
//            int dotPosition=file1.getName().lastIndexOf('.');
//            String ext = file.getName().substring(dotPosition);
//            String fileId=storageService.put(file1.getInputStream(), ext);
//        message=fileId;
//        }
//        else{
//        message="NO file submitted";
//        }
//        resp.getWriter().print(message);
//        
//        FileItem picture=formParseResult.getFiles().get("picture");
//        String name=formParseResult.getFields().get("name");
//        String price=formParseResult.getFields().get("price");
//        String description=formParseResult.getFields().get("description");
//        String code=formParseResult.getFields().get("code");
//        String stock=formParseResult.getFields().get("stock");
//        String categoryId=formParseResult.getFields().get("category");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    String type = req.getParameter("type");
//    if("categories".equals(type)){//.../product?type=categories
//    getCategories(req, resp);
//    }
//    else if("category".equals(type)){//.../product?type=categoriry&id=12312
//    getCategory(req, resp);
//    }
//    else{
//    getProducts(req, resp);
//    }
//    }
//    private void getCategories(HttpServletRequest req, HttpServletResponse resp)throws ServletException
//    {
//    
//    }
//    private void getCategory(HttpServletRequest req, HttpServletResponse resp)throws ServletException
//    {
//    
//    }
//    private void getProducts(HttpServletRequest req, HttpServletResponse resp)throws ServletException
//    {
//    
//    }
//}
