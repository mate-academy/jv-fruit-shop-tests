package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {
    public static void writeDataToFile(String filePath, String data) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file " + filePath, e);
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new java.io.FileWriter(filePath, false))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }
}
