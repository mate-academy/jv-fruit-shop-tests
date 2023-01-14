package core.basesyntax.service.impl;

import core.basesyntax.exception.WrongPathException;
import core.basesyntax.service.ReadCsvFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ReadCsvFileServiceImpl implements ReadCsvFileService {
    private static final int FILE_HEADER_LINE = 1;

    @Override
    public List<String> readFromFile(String path) {
        if (path != null) {
            try {
                return Files.readAllLines(Path.of(path)).stream()
                        .skip(FILE_HEADER_LINE).collect(Collectors.toList());
            } catch (IOException e) {
                throw new WrongPathException(
                        String.format("Can`t get data from file %s", path));
            }
        } else {
            throw new WrongPathException("Can`t get data from null path");
        }
    }
}
