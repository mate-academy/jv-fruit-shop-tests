package core.basesyntax.service.impl;

import core.basesyntax.exception.WriteToFileException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FileWriterImpl implements FileWriter {

    @Override
    public void writeTo(String filePath, List<String> rows) {
        List<String> fileContent = rows == null ? Collections.emptyList() : rows;
        try {
            if (filePath == null) {
                throw new WriteToFileException();
            }
            Files.write(Path.of(filePath), fileContent);
        } catch (IOException | WriteToFileException e) {
            throw new WriteToFileException("Can't write to file " + filePath);
        }
    }
}
