
package itstep.learning.services.hash.storage;

import java.io.IOException;
import java.io.InputStream;

//принимает файлы на сберегание
public interface StorageService {
    String put(InputStream inputStream, String ext) throws IOException; //возвращает ID & name he choose//ext =расширение файла
    InputStream get(String itemId)throws IOException; //даем ID - получаем stream
    
}
