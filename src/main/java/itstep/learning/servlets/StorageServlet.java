package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.services.hash.storage.StorageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

@Singleton
public class StorageServlet extends HttpServlet {

    private final StorageService storageService;
    private final Set<String> blacklist=Set.of(
            ".exe", ".php", ".py",".pyd", ".dll", ".cmd", ".com");

    @Inject
    public StorageServlet(StorageService storageService) {
        this.storageService = storageService;
    }

       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileId = req.getPathInfo().substring(1);
        try( InputStream inputStream = storageService.get( fileId ) ) {
            int dotPosition = fileId.lastIndexOf( '.' );
            String ext = fileId.substring( dotPosition ) ;
            resp.setContentType( mimeByExtension( ext ) );
            OutputStream writer = resp.getOutputStream();
            byte[] buf = new byte[131072];
            int len;
            while( ( len = inputStream.read(buf) ) > 0 ) {
                writer.write( buf, 0, len );
            }
        }
        catch( IOException ex ) {
            resp.setStatus( 404 );
        }
    
    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //String fileId = req.getPathInfo().substring(1);
//    
//        String fileId = req.getPathInfo();
//        if(fileId==null||fileId.isBlank()||!fileId.contains(".")){
//        resp.setStatus(400);
//        return;
//        }
//       
//        String ext=fileId.substring(fileId.lastIndexOf('.')).trim();//находим последнюю точку и берем то, что после нее
//    
//        if (ext.isBlank()||blacklist.contains(ext.toLowerCase())){
//        resp.setStatus(400);//если пусто после точки то error.
//        }
//        fileId=fileId.substring(1);
//       
//        try (InputStream inputStream = storageService.get(fileId)) {
//           // String ext = fileId.substring(fileId.lastIndexOf('.'));
//            resp.setContentType(mimeByExtension(ext));
//          //  resp.setContentType("image/png");
//            OutputStream writer = resp.getOutputStream();
//            byte[] buf = new byte[131072];
//            int len;
//            while ((len = inputStream.read(buf)) > 0) {
//                writer.write(buf, 0, len);
//            }
//        } catch (IOException ex) {
//            resp.setStatus(404);
//        }
//    }

    private String mimeByExtension(String ext) {
        switch (ext) {
            case ".jpg":
                ext = ".jpeg";
            case ".jpeg":
            case ".png":
            case ".gif":
            case "webp":
            case ".bmp":
                return "image/" + ext.substring(1);

            case ".txt":
                ext = ".plain";
            case ".css":
            case ".csv":
            case ".html":
                return "text/" + ext.substring(1);

            case ".js":
            case ".mjs":
                return "text/javascript";

            default:
                return "application/octet-stream";
        }
    }
}

//
////http://localhost:8080/Java-Web-221/storage/123?x=10&y=20
////
////req.getMethod()       GET
////
////req.getRequestURI()   /Java-Web-221/storage/123
////
////req.getContextPath()  /Java-Web-221
////
////req.getServletPath()  /storage
////
////req.getPathInfo()     /123
////
////req.getQueryString()  x=10&y=20
////
//// 
