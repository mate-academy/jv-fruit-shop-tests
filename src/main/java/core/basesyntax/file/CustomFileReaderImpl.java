package core.basesyntax.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReaderImpl implements CustomFileReader {
    @Override
    public List<String> read(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file at " + filePath, e);
        }
        return lines;
    }
}

