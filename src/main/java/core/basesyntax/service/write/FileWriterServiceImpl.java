package core.basesyntax.service.write;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String record, String file) {
        if (record == null || record.isEmpty()) {
            throw new IllegalArgumentException("Record cannot be null or empty");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(record);
        } catch (IOException e) {
            throw new RuntimeException("Can't write record to file", e);
        }
    }
}
