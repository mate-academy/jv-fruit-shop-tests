package core.basesyntax.service.impl;

import core.basesyntax.service.CustomFileWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements CustomFileWriter {
    private final String filePath;

    public FileWriterImpl() {
        this.filePath = "src/main/resources/finalReport.csv";
    }

    public FileWriterImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(String resultingReport) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(resultingReport);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to file", e);
        }
    }
}
