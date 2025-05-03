package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileServiceImpl implements FileService {
    @Override
    public List<String> readFile(String filePath) {
        isFilePathNull(filePath);
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read file " + filePath + ", " + e);
        }
    }

    @Override
    public void writeToFile(String report, String filePath) {
        isNullReport(report);
        isFilePathNull(filePath);
        doesFileExist(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file "
                    + filePath + ", " + e);
        }
    }

    private void isNullReport(String report) {
        if (report == null) {
            throw new IllegalArgumentException("Report can't be null");
        }
    }

    private void isFilePathNull(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path can't be null");
        }
    }

    private void doesFileExist(String filePath) {
        if (!Files.exists(Path.of(filePath))) {
            throw new IllegalArgumentException("File does not exist");
        }
    }
}
