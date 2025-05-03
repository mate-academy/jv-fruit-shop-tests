package core.basesyntax.servise.impl;

import core.basesyntax.servise.FileReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> read(String filePath) {
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                fileLines.add(line);
                line = br.readLine();
            }
            return fileLines;
        } catch (IOException e) {
            throw new RuntimeException("Can't read " + filePath, e);
        }
    }
}
