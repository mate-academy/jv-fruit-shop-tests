package core.basesyntax.service.impl;

import core.basesyntax.exceptions.EmptyFileException;
import core.basesyntax.exceptions.ExistFileException;
import core.basesyntax.exceptions.NullException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.DataParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {
    private static final int HEAD_LINE = 1;
    private static final DataParser parser = new DataParserImpl();

    @Override
    public List<FruitTransaction> readFromFile(String pathToFile) {
        checkPathToFile(pathToFile);
        List<String> inputData;
        File file = new File(pathToFile);
        try {
            inputData = Files.readAllLines(Path.of(pathToFile));
            checkFile(inputData, file);
        } catch (IOException e) {
            throw new ExistFileException("Can`t read file: " + file.getName(), e);
        }
        return inputData.stream()
                .skip(HEAD_LINE)
                .map(parser::getData)
                .collect(Collectors.toList());
    }

    private void checkFile(List<String> inputData, File file) {
        if (inputData.isEmpty()) {
            throw new EmptyFileException("File " + file.getName() + " is empty");
        }
    }

    private void checkPathToFile(String pathToFile) {
        if (pathToFile == null) {
            throw new NullException("Path to file is null");
        }
    }
}
