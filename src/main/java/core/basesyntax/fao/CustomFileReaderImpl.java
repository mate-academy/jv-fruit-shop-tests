package core.basesyntax.fao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReaderImpl implements CustomFileReader {

    public List<String> read(String fileName) {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                    StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }

        return lines;
    }
}

