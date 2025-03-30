package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    private static final String HEADER = "fruit,quantity";

    @Override
    public void write(String outputData, String fileName) {
        if (outputData == null) {
            throw new RuntimeException("Input data can't be null");
        }
        if (fileName == null) {
            throw new RuntimeException("File name cannot be null");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(HEADER);
            bufferedWriter.newLine();
            bufferedWriter.write(outputData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + fileName, e);
        }
    }
}
