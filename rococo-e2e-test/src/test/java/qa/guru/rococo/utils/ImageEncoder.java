package qa.guru.rococo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageEncoder {

    public static String encode(File file) {
        String encoded = null;
        try {
            // Чтение файла в байтовый массив
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64," + encoded ;
    }
}
