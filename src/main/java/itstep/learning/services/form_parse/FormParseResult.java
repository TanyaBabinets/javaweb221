package itstep.learning.services.form_parse;


import java.util.Map;
import org.apache.commons.fileupload.FileItem;


public interface FormParseResult {
   Map<String, String> getFields();
   Map<String, FileItem> getFiles();
    //FormParseResult parseRequest( HttpServletRequest req )throws IOException;
}
//Завантаження файлів, розбір даних форм
//При передачі файлів форми використовують спец. тип запитів - multipart
//При прийомі файлів необхідно забезпечити
//а) їх тимчасове зберігання - зазвичай у системних ресурсах 
//б) по