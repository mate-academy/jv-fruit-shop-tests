package mate.academy.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import mate.academy.service.FileReaderService;

public class CsvFileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFromFile(String filePath) {
        List<String> dataFromFile;
        if (!filePath.endsWith(".csv")) {
            throw new RuntimeException("File is not .csv");
        }
        try {
            dataFromFile = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + filePath);
        }
        if (dataFromFile.size() == 0) {
            throw new RuntimeException("Can't get information from empty file.");
        }
        return dataFromFile;
    }
}
