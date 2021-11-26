package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import service.FileWriterService;

public class FileWriterServiceImpl implements FileWriterService {

    @Override
    public boolean write(String outputFile, List<String> report) {
        if (outputFile == null) {
            throw new RuntimeException("the path cannot be null");
        }
        try {
            Files.write(Paths.get(outputFile), report);
        } catch (IOException e) {
            throw new RuntimeException("can`t write in file");
        }
        return true;
    }
}
