package service.impl;

import exception.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> read(String fileName) {
        if (fileName == null) {
            throw new ValidationException("File name can't be " + fileName);
        }
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new ValidationException("Can't find data with this path:  " + fileName, e);
        }
        return dataFromFile;
    }
}
