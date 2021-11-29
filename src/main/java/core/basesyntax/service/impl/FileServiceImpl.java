package core.basesyntax.service.impl;

import core.basesyntax.service.FileService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileServiceImpl implements FileService {
    @Override
    public List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("No such filePath " + filePath, e);
        }
    }

    @Override
    public boolean writeToReportFile(String informationString, String filePath) {
        File reportFile = new File(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile))) {
            bufferedWriter.write(informationString);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("No such file" + filePath);
        }
    }
}
