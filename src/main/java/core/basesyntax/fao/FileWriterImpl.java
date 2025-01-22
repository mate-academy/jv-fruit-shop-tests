package core.basesyntax.fao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements CustomFileWriter {

    @Override
    public void write(String fileName, String info) {
        String filePath = "src/main/resources/" + fileName;

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                bufferedWriter.write(info);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file " + fileName, e);
        }
    }
}
