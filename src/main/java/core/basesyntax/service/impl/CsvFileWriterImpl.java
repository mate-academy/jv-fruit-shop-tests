package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriterImpl implements CsvFileWriter {
    @Override
    public void write(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + filename, e);
        }
    }
}
