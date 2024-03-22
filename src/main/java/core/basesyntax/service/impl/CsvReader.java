package core.basesyntax.service.impl;

import core.basesyntax.exception.FileReadingFailureException;
import core.basesyntax.service.DataReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader implements DataReader {
    private static final String READING_FAILURE_MESSAGE = "Failed to read from the file";

    @Override
    public List<String> read(String fileName) {
        try {
            checkFileValidity(fileName);
            Path path = Paths.get(fileName);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileReadingFailureException(READING_FAILURE_MESSAGE + fileName, e);
        }
    }

    private boolean inputFileExtensionIsCsv(String fileName) {
        return com.google.common.io.Files.getFileExtension(fileName).equals("csv");
    }

    private void checkFileValidity(String fileName) throws IOException {
        if (fileName == null || !inputFileExtensionIsCsv(fileName)
                            || Files.size(Paths.get(fileName)) == 0) {
            throw new IOException();
        }
    }
}
