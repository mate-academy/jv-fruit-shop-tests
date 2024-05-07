package core.basesyntax.servise.impl;

import core.basesyntax.servise.WriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class CsvFileWriterServiceImpl implements WriterService {
    static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;

    @Override
    public void writeToFile(String pathOutFile, String report) {
        if (!report.startsWith(TITLE)) {
            throw new IllegalArgumentException("Incorrect format report");
        }
        try {
            java.nio.file.Paths.get(pathOutFile);
        } catch (InvalidPathException err) {
            throw new IllegalArgumentException("Incorrect path file" + err);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathOutFile))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + pathOutFile, e);
        }
    }
}
