package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeFile(String fileName, String report) {
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName))) {
            bw.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + fileName, e);
        }
    }
}
