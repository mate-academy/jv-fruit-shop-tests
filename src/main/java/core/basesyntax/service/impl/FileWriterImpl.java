package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NoSuchCsvFileException;
import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String line, String filePath) {
        File file = new File(filePath);
        if (!file.getName().endsWith(".csv")) {
            throw new NotCsvFileException("This is not CSV file!");
        }
        if (!file.exists()) {
            throw new NoSuchCsvFileException("There is no such file" + filePath);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file))) {
            bufferedWriter.append(line);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to " + filePath);
        }
    }
}
