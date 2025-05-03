package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderService implements FileReaderService {
    @Override
    public List<String> readFile(File inputFile) {
        if (inputFile != null) {
            try {
                return Files.readAllLines(inputFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException("Can’t read file", e);
            }
        }
        return new ArrayList<>();
    }
}
