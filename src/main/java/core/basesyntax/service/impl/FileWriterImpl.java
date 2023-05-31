package core.basesyntax.service.impl;

import core.basesyntax.service.DataWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileWriterImpl implements DataWriter {
    private final String csvFilePath;
    private final byte[] reportData;

    public FileWriterImpl(String csvFilePath, byte[] reportData) {
        this.csvFilePath = csvFilePath;
        this.reportData = reportData;
    }

    @Override
    public void writeData() {
        try {
            Optional.ofNullable(csvFilePath).orElseThrow(
                    () -> new RuntimeException("File path can`t be null"));
            Optional.ofNullable(reportData).orElseThrow(
                    () -> new RuntimeException("Recordable data can`t be null"));
            Files.write(Path.of(csvFilePath), reportData);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Unable to write data to file by path: %s", csvFilePath));
        }
    }
}
