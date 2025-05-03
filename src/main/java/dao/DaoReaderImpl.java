package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DaoReaderImpl implements DaoReader {
    @Override
    public List<String> get(String fileName) {
        if (fileName == null) {
            throw new RuntimeException("File name is null");
        }
        try {
            Path path = Path.of(fileName);
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException("Can't get data from file " + fileName);
        }
    }
}
