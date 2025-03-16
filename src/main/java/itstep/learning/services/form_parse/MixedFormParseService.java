package itstep.learning.services.form_parse;


import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map; 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@Singleton
public class MixedFormParseService implements FormParseService {
    private final ServletFileUpload uploader;

    @Inject
    public MixedFormParseService() {
        // Create a factory for disk-based file items
DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(10*1024*1024);
        factory.setRepository(new File("C:/tmp"));
//                .builder()
//                .setBufferSize( 1 * 1024 * 1024 )
//                .setPath( "C:/tmp" )
//                .get();
        uploader = new ServletFileUpload( factory );
        uploader.setSizeMax( 20 * 1024 * 1024 );
    }
    
    
    @Override
    public FormParseResult parseRequest( HttpServletRequest req ) throws IOException {
        Map<String, String> fields = new HashMap<>();
        Map<String, FileItem> files = new HashMap<>();
        
        if( req.getContentType().startsWith( "multipart/form-data" ) ) {
            try{}
            catch(Exception ex){}
        
//            List<FileItem> fileItems = uploader.parseRequest( req );
//            for( FileItem fileItem : fileItems ) {                
//                if( fileItem.isFormField() ) {
//                    fields.put( 
//                            fileItem.getFieldName(), 
//                            fileItem.getString( StandardCharsets.UTF_8 ) 
//                    ) ;
//                }
//                else {
//                    files.put( 
//                            fileItems.getFieldName(),
//                            fileItems
//                    );
//                }
//            }
        }
        else {   // не-multipart запит, видобуваємо поля через параметри
            Enumeration<String> names = req.getParameterNames();
            while( names.hasMoreElements() ) {
                String name = names.nextElement() ;
                fields.put( name, req.getParameter( name ) ) ;
            }
        }
        
        return new FormParseResult() {
            @Override
            public Map<String, String> getFields() {
                return fields;
            }

            @Override
            public Map<String, FileItem> getFiles() {
                return files;
            }            
        };
    }
    
}
/*
Змішана імплементація - якщо запит приходить multipart, то 
використовуємо Apache, інакше - Servlet-API
*/

