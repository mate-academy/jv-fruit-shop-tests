package core.basesyntax.serviceimpl;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            if (lines.isEmpty()) {
                throw new RuntimeException("Error reading data from file. File is empty "
                        + filePath);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Error reading data from file " + filePath, e);
        }
    }
}
