package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String data, String file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file))) {
            bufferedWriter.write(data);
        } catch (Exception e) {
            throw new RuntimeException("Report cannot be write to file " + file, e);
        }
    }
}
