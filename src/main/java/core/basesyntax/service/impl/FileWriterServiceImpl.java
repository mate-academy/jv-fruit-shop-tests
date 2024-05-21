package core.basesyntax.service.impl;

import core.basesyntax.exception.WritingException;
import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(String fileName, String outputData) {
        if (outputData == null) {
            throw new WritingException("The outputData = null.");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(outputData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + fileName);
        }
    }
}
