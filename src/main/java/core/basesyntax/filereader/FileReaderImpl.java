package core.basesyntax.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        List<String> inputReport = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputReport.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error reading file: " + filePath, e);
        }
        return inputReport;
    }
}
