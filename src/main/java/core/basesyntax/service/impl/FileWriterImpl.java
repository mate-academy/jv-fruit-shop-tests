package core.basesyntax.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import core.basesyntax.service.FileWriter;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(List<String> data, String filePath) {
        try {
            Files.write(Path.of(filePath), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
