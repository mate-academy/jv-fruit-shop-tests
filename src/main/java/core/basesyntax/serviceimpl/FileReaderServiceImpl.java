package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    private static final int DEFAULT_TEXT_INDEX = 1;

    @Override
    public List<String> getInputData(String fileName) {
        if (fileName == null) {
            throw new InvalidDataException("File name can't be null");
        }
        File file = new File(fileName);
        try {
            return Files.readAllLines(file.toPath()).stream()
                    .skip(DEFAULT_TEXT_INDEX)
                    .toList();
        } catch (IOException e) {
            throw new InvalidDataException("Can't create report from this file: " + fileName);
        }
    }
}
