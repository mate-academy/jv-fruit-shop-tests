package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements core.basesyntax.service.FileReader {

    @Override
    public List<String> read(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new RuntimeException("No file at such path: " + filePath);
        }
        List<String> output = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String value;
            while ((value = reader.readLine()) != null) {
                output.add(value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading file: " + filePath, e);
        }
        return output;
    }
}
