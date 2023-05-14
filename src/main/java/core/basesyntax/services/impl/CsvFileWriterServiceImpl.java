package core.basesyntax.services.impl;

import core.basesyntax.exceptions.InvalidPathException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    @Override
    public void writeToCsvFile(List<String> data, Path filePath) {
        if (data == null) {
            throw new NullDataException("Can't write null data in file.");
        }
        try {
            Files.write(filePath,data);
        } catch (IOException | NullPointerException e) {
            throw new InvalidPathException("Can't write report into file " + filePath, e);
        }
    }
}
