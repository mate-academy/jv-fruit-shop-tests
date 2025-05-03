package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String report, String filePath) {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(filePath)) {
            fileWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + filePath, e);
        }
    }
}
