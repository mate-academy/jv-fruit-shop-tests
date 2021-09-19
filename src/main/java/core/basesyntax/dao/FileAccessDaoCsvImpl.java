package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileAccessDaoCsvImpl implements FileAccessDaoCsv {

    @Override
    public List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file located at: " + filePath);
        }
    }

    @Override
    public void writeToFile(String stringForReport, String outFilePath) {
        File toFile = new File(outFilePath);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile))) {
            bufferedWriter.write(stringForReport);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + toFile);
        }
    }
}
