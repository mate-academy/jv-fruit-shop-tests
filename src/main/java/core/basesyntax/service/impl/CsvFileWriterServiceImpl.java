package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NullException;
import core.basesyntax.exceptions.ReportException;
import core.basesyntax.service.CsvFileWriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    @Override
    public void writeToFile(String pathToFile, String[] report) {
        checkReport(report);
        checkPathToFile(pathToFile);
        File file = new File(pathToFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String line: report) {
                writer.write(line);
                writer.write(System.lineSeparator());
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file: " + file.getName(), e);
        }
    }

    private void checkPathToFile(String pathToFile) {
        if (pathToFile == null) {
            throw new NullException("Path to file is null");
        }
    }

    private void checkReport(String[] report) {
        if (report == null) {
            throw new NullException("Report is null");
        }
        if (report.length == 0) {
            throw new ReportException("Report is empty");
        }
    }
}
