package core.basesyntax.service.impl;

import core.basesyntax.exceptions.ReportSenderException;
import core.basesyntax.service.ReportSender;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportSenderCsvImpl implements ReportSender {
    private String filePath;

    public ReportSenderCsvImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void send(String report) {
        if (report == null || report.isEmpty()) {
            throw new ReportSenderException("Can't send report. Report is null or empty");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + filePath, e);
        }
    }
}
