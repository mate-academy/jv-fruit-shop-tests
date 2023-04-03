package core.basesyntax.services;

import core.basesyntax.services.exception.FileWriterException;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String toFile, String report) {
        try (FileWriter writer = new FileWriter(toFile)) {
            writer.write(report);
        } catch (IOException e) {
            throw new FileWriterException("Can't write data to file " + toFile);
        }
    }
}
