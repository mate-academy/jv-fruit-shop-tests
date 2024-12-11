package core.basesyntax.data.dao;

import core.basesyntax.data.exeption.UnknownOperationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeFile(String filePath, String report) {
        try {
            Files.writeString(Path.of(filePath), report);
        } catch (IOException e) {
            throw new UnknownOperationException("Can't write data to file: " + filePath, e);
        }
    }
}
