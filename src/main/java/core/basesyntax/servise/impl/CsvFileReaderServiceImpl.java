package core.basesyntax.servise.impl;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.servise.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileReaderServiceImpl implements ReaderService {
    private static final int TITLE_INDEX = 0;
    private static final String TITLE = "type,fruit,quantity";

    @Override
    public List<String> readFromFile(String pathInnFile) {
        List<String> inputList;
        try (Stream<String> streamFromFile = Files.lines(Paths.get(pathInnFile))) {
            inputList = streamFromFile.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + pathInnFile, e);
        }
        if (inputList.isEmpty()) {
            throw new FileReaderException("The file " + pathInnFile + " is empty.");
        } else if (!inputList.get(TITLE_INDEX).equals(TITLE)) {
            throw new FileReaderException("Invalid file format.");
        } else {
            return inputList;
        }
    }
}
