package service.impl;

import exception.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import service.WriterService;

public class WriterServiceImpl implements WriterService {
    @Override
    public void write(String resultingReport, String fileName) {
        if (fileName == null || resultingReport == null) {
            throw new ValidationException("File name can't be " + fileName);
        }
        try {
            Files.write(Path.of(fileName), resultingReport.getBytes());
        } catch (IOException e) {
            throw new ValidationException("Can't write to file" + fileName, e);
        }
    }
}
