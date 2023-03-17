package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriterService implements FileWriterService {
    @Override
    public File saveToFile(File reportFile, List<String> report) {
        if (report != null) {
            createNewReportFile(reportFile);
            report.forEach(line -> write(reportFile, line));
        }
        return reportFile;
    }

    private boolean createNewReportFile(File reportFile) {
        if (reportFile.exists()) {
            reportFile.delete();
        }
        try {
            return reportFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can’t create report file.", e);
        }
    }

    private void write(File reportFile, String line) {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(reportFile, true))) {
            bufferedWriter.write(line + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Can’t write data to file", e);
        }
    }
}
