package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String report, String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(filePath))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Report " + report
                    + " can`t be write to file " + filePath, e);
        }
    }
}
