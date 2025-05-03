package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriterService {
    @Override
    public void writeDataToFile(String inputData, String filePathName) {
        if (inputData == null || inputData.isEmpty()) {
            throw new IllegalArgumentException(" Input data is empty");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePathName));
            writer.write(inputData);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("can't write data to file");
        }
    }
}

