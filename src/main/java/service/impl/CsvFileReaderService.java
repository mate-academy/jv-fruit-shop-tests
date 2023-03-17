package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import service.ReaderDataService;

public class CsvFileReaderService implements ReaderDataService {
    @Override
    public List<String> read(String path) {
        try {
            List<String> result = Files.readAllLines(Path.of(path));
            if (result.size() == 0) {
                throw new RuntimeException("File can't be empty");
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Can't find the file: " + path, e);
        }

    }
}
