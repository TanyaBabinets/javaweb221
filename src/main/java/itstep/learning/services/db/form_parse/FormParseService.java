
package itstep.learning.services.db.form_parse;

import jakarta.servlet.http.HttpServletRequest;


public interface FormParseService {
    FormParseResult parseRequest (HttpServletRequest req );


    }

