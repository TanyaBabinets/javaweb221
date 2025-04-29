package itstep.learning.services.hash.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.ProductDao;
import itstep.learning.services.config.ConfigService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Singleton
public class DiskStorageService implements StorageService {

    private String storagePath;
    private ProductDao productDao;

    @Inject
    public DiskStorageService(ConfigService configService) {
        this.storagePath = configService.get("storage.path");
        if (this.storagePath == null || this.storagePath.isEmpty()) {
            this.storagePath = "D:/storage/java211/";
        }
    }

    @Override
    public String put(InputStream inputStream, String ext) throws IOException {
        String itemId = UUID.randomUUID() + ext;
        File file = new File(storagePath + itemId);
        try (FileOutputStream writer = new FileOutputStream(file)) {
            byte[] buf = new byte[131072];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                writer.write(buf, 0, len);
            }
         } catch (IOException e) {
            throw new IOException("Error !!!!!! writing file to disk", e);
        } finally {
                      if (inputStream != null) {
                inputStream.close();
            }
        }

        return itemId;
    }

    
    @Override
    public boolean delete(String itemId) {
        if (itemId == null || itemId.isBlank()) {
            return false; 
        }


        File file = new File(storagePath + itemId);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    @Override
    public InputStream get(String itemId) throws IOException {
        return new FileInputStream(storagePath + itemId);
    }

}
