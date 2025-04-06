package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileReader {
    private static final String DEFAULT_INPUT_FILE = "reportToRead.csv";

    @Override
    public List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        Path filePath = Path.of(fileName != null && !fileName.isBlank()
                ? fileName : DEFAULT_INPUT_FILE);

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + filePath, e);
        }
        return lines;
    }
}
