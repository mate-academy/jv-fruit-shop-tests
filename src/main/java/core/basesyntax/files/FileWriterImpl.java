package core.basesyntax.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements CustomFileWriter {

    @Override
    public void write(String fileName, String info) {
        Path filePath = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(filePath.toFile()))) {
            bufferedWriter.write(info);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file " + fileName, e);
        }
    }
}
