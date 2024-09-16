package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> read(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filePath))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
            }
            return List.of(fileContent.toString().split(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("can not read file: " + filePath, e);
        }
    }
}
