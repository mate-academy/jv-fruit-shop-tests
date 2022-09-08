package core.basesyntax.services.impl;

import core.basesyntax.services.FileReaderService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public List<String> read(String filePath) {
        List<String> rows = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                rows.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + filePath, e);
        }
        return rows;
    }
}
