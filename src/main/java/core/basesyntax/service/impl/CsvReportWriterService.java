package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvReportWriterService implements ReportWriterService {
    @Override
    public void writeReport(String report, String reportPath) {
        if (report == null) {
            throw new RuntimeException("Report can't be null");
        }
        try (BufferedWriter bufferedWriter
                     = new BufferedWriter(new FileWriter(new File(reportPath)))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }
}
