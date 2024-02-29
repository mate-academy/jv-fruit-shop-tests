package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

public class FileWriterCsvImpl implements Writer {
    private static final String CSV_EXTENSION = ".csv";
    private static final int FILE_EXTENSION_LENGTH = 4;

    @Override
    public void write(String fileName, List<String> report) {
        if (fileName == null || report == null) {
            throw new IllegalArgumentException("""
                    Parameters can't be null, but:"
                    fileName = '%s'
                    report = '%s'""".formatted(fileName, report));
        }
        if (report.isEmpty()) {
            throw new RuntimeException("Report is empty");
        }
        if (fileName.isEmpty()) {
            throw new RuntimeException("File name is empty");
        }
        String currentFileExtension = fileName.substring(fileName.length()
                - FILE_EXTENSION_LENGTH);
        if (!currentFileExtension.equals(CSV_EXTENSION)) {
            throw new RuntimeException("""
                    The file extension is different from .csv: %s"""
                .formatted(fileName));
        }
        File targetFile = new File(fileName);
        targetFile.delete();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String line : report) {
                if (line == null) {
                    throw new RuntimeException("Report contains null value");
                }
                bufferedWriter.append(line);
                bufferedWriter.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new InvalidPathException(fileName, "File path is not valid");
        }
    }
}

