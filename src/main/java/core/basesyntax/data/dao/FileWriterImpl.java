package core.basesyntax.data.dao;

import core.basesyntax.data.exeption.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterImpl implements FileWriter {
    @Override
    public List<String> writeFile(String filePath, String report) {
        try {
            Files.writeString(Path.of(filePath), report);
        } catch (IOException e) {
            throw new FileException("Can't write data to file: " + filePath, e);
        }
        return null;
    }
}
