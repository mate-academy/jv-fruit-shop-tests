package basesyntax.service.impl;

import basesyntax.exceptions.CustomRuntimeException;
import basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeToFile(String stringToWrite, String filePath) {
        if (filePath == null || stringToWrite == null) {
            throw new CustomRuntimeException("Some argument is equal to null");
        }
        File file = new File(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(stringToWrite);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file.", e);
        }
    }
}
