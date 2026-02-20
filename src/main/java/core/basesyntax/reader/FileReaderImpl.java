package core.basesyntax.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String file) {
        if (file == null || file.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or blank");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file))) {
            List<String> readLines = new ArrayList<>();
            String value = bufferedReader.readLine();

            while (value != null) {
                readLines.add(value);
                value = bufferedReader.readLine();
            }
            return readLines;

        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + file);
        }
    }
}
