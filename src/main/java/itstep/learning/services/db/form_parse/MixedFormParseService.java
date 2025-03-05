//package itstep.learning.services.db.form_parse;
//
//import com.google.inject.Inject;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//public class MixedFormParseService implements FormParseService {
//
//    private final ServletFileUpload uploader;
//
//    @Inject
//    public MixedFormParseService() {
//
//        // / Create a factory for disk-based file items  //10MB
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setSizeThreshold(10 * 1024 * 1024);
//        factory.setRepository(new File("C:/tmp"));
//        uploader = new ServletFileUpload(factory);
//        uploader.setSizeMax(20 * 1024 * 1024);
//
//    }
//
//    @Override
//    public FormParseResult parseRequest(HttpServletRequest req) {
//        Map<String, String> fields = new HashMap<>();
//        Map<String, FileItem> files = new HashMap<>();
//        
////        if(req.getContentType().startsWith( "multipart/form-data" )){
////            try{
////        List<FileItem> items= uploader.parseRequest(req);//.forEach( fileItem->{});
////            }catch(Exception ex){}
////        }
//      //  else{//not multipart запрос, получаем поля через обычные гет-параметры
//        Enumeration<String> names = req.getParameterNames();
//        while ( names.hasMoreElements()){
//        String name=names.nextElement();
//        fields.put(name, req.getParameter(name));//проходим по всем именам что есть в параметре
//        }
//   //     }
//
//      //  return new FormParseResult() {
//            @Override
//            public Map<String, String> getField() {
//                return fields;
//            }
//            @Override
//            public Map<String, FileItem> getFiles() {
//           return files;
//            }
//
//        };
//        
//    }
//
//}
