package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String data, String pathToFile) {
        File requiredFile = new File(pathToFile);
        try {
            requiredFile.createNewFile();
            Files.write(requiredFile.toPath(), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't create file " + pathToFile, e);
        }
    }
}
