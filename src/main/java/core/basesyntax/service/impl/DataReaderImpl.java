package core.basesyntax.service.impl;

import core.basesyntax.service.DataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReaderImpl implements DataReader {
    @Override
    public List<String> read(String path) {
        String line = "";
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + path, e);
        }
        if (fileContent.isEmpty()) {
            throw new RuntimeException("File is empty!");
        }
        return fileContent;
    }
}
