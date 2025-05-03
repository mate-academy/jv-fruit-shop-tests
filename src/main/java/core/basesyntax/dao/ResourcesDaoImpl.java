package core.basesyntax.dao;

import core.basesyntax.service.FileFormatValidator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ResourcesDaoImpl implements ResourcesDao {
    private FileFormatValidator fileFormatValidator = new FileFormatValidator();

    @Override
    public List<String> readFromFile(String filePath) {
        try {
            String firstLine = Files.readString(Path.of(filePath));
            fileFormatValidator.validate(firstLine);
            return Files.lines(Path.of(filePath))
                        .skip(1)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from File: " + filePath);
        }
    }

    @Override
    public void writeToFile(String filePath, List<String> data) {
        try {
            Files.write(Path.of(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file: " + filePath);
        }
    }
}
