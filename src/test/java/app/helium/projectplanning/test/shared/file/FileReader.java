package app.helium.projectplanning.test.shared.file;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;


public class FileReader {
    public static String readJsonFromFile(String locationRelativeToClassPath) {
        try {
            File file = new ClassPathResource(locationRelativeToClassPath).getFile();
            return readFileToString(file, StandardCharsets.UTF_8);
        }
        catch (IOException ioException) {
            ioException.fillInStackTrace();
            throw new RuntimeException("Failed to read file");
        }
    }
}
