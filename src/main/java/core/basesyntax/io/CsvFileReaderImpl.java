package core.basesyntax.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderImpl implements CsvFileReader {
    private static final int HEADER_INDEX = 0;

    @Override
    public List<String> read(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new RuntimeException("File path cannot be null or empty");
        }

        List<String> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            int lineIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (lineIndex == HEADER_INDEX) {
                    lineIndex++;
                    continue;
                }

                lineIndex++;

                if (!line.isBlank()) {
                    result.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + filePath, e);
        }

        return result;
    }
}
