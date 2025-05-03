package core.basesyntax.service.impl;

import core.basesyntax.exception.WrongFileTypeException;
import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderImpl implements Reader {
    private static final int OLD_HEADER_INDEX = 1;
    private static final String FILE_FORMAT = ".csv";

    @Override
    public List<String> read(String fromFileName) {
        try {
            if (fromFileName.endsWith(FILE_FORMAT)) {
                return Files.readAllLines(Path.of(fromFileName))
                  .stream()
                  .skip(OLD_HEADER_INDEX)
                  .collect(Collectors.toList());
            }
            throw new WrongFileTypeException("Cant read info from not csv file");
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file ");
        }
    }
}
