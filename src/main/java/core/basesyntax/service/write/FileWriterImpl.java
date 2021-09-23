package core.basesyntax.service.write;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {

    @Override
    public boolean write(String data, String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(filePath))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Cant write to file" + filePath);
        }
        return true;
    }
}
