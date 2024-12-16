package core.basesyntax.data.dao;

import core.basesyntax.data.exeption.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> readFile(String filePath) {
        List<String> reportList;
        try {
            reportList = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new FileException("Can't read data from file: " + filePath, e);
        }
        return reportList;
    }
}
