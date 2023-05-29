package core.basesyntax.service.impl;

import core.basesyntax.exeptions.WrongExtensionFile;
import core.basesyntax.service.FileReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderServiceCsvImpl implements FileReaderService {
    private static final String FILE_FORMAT = ".csv";

    @Override
    public List<String> readFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            if (filePath.endsWith(FILE_FORMAT)) {
                return bufferedReader.lines().collect(Collectors.toList());
            }
            throw new WrongExtensionFile("Wrong extension of file: "
                    + filePath + ", must be '" + FILE_FORMAT + "' file");
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + filePath, e);
        }
    }
}
