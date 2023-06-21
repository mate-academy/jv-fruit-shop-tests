package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class ReaderServiceImpl implements ReaderService {
    private static final String VALID_FORMAT = ".csv";
    private static final String EXCEPTION_MESSAGE_FOR_FILEPATH = "FilePath argument is null";
    private static final String EXCEPTION_MESSAGE_FOR_READING = "Can't read data from file: ";
    private static final String EXCEPTION_MESSAGE_FOR_FILE_CONTENT = "Input file is empty: ";
    private static final String EXCEPTION_MESSAGE_FOR_FILE_FORMAT
            = "Incorrect file format. Expected .csv: ";

    @Override
    public List<String> readFromFile(String filePath) {
        String sourceFile = Optional.ofNullable(filePath)
                .orElseThrow(() -> new TransactionException(EXCEPTION_MESSAGE_FOR_FILEPATH));
        if (!sourceFile.endsWith(VALID_FORMAT)) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_FILE_FORMAT + filePath);
        }
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(sourceFile));
        } catch (IOException e) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_READING + filePath);
        }
        if (data.isEmpty()) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_FILE_CONTENT + filePath);
        }
        return data;
    }
}
