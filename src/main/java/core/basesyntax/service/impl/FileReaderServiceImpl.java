package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFromFile(String filePath) {
        if (filePath == null) {
            throw new RuntimeException("Path to file can not be null!");
        }
        File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            throw new RuntimeException("Source file does not exist: path to file " + filePath
            + "is wrong!");
        }
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile))) {
            String lineFromFile = bufferedReader.readLine();
            while (lineFromFile != null) {
                lines.add(lineFromFile);
                lineFromFile = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Read error from file " + sourceFile + "!", e);
        }
        return lines;
    }
}
