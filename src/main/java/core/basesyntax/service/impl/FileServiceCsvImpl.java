package core.basesyntax.service.impl;

import core.basesyntax.service.FileService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileServiceCsvImpl implements FileService {
    @Override
    public List<String> readFile(String fileName) {
        Path path = Path.of(fileName);
        try {
            if (Files.size(path) == 0) {
                throw new IOException("File is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException("File is empty");
        }
        try {
            if (Files.size(path) == 0) {
                throw new IOException("File is empty");
            }
            return Files.readAllLines(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    @Override
    public void writeFile(String report, String fileName) {
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(report);
            System.out.println("File created at: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("File not created at: " + fileName, e);
        }
    }
}
