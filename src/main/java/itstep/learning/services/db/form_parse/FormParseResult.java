/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itstep.learning.services.db.form_parse;

import java.util.Map;
import org.apache.commons.fileupload.FileItem;


public interface FormParseResult {
    Map<String, String> getFields();
    Map<String, FileItem> getFiles();
    
}
