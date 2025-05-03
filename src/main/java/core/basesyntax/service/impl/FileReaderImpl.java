package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NotCsvFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements core.basesyntax.service.FileReader {
    @Override
    public List<String> read(String filePath) {
        File file = new File(filePath);
        if (!file.getName().endsWith(".csv")) {
            throw new NotCsvFileException("This is not CSV file!");
        }
        if (!file.exists()) {
            throw new RuntimeException("There is not such file as " + filePath);
        }
        List<String> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read " + filePath);
        }
        return result;
    }
}
