package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileWriterServiceImpl implements FileWriterService {

    @Override
    public void writeToFile(String result, String fileName) {
        if (fileName == null) {
            throw new RuntimeException("File name can`t be null");
        }
        File file = new File(fileName);
        try {
            Files.write(file.toPath(), result.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to the file: " + file.getName(), e);
        }
    }
}
