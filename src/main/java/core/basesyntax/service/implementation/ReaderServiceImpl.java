package core.basesyntax.service.implementation;

import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFromFile(String filePath) {
        if (filePath == null) {
            throw new RuntimeException("File path cannot be null!");
        }
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file " + filePath, e);
        }
        return lines;
    }
}
