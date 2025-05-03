package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriter;
import core.basesyntax.service.impl.exception.InvalidDataException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriterImpl implements ReportWriter {
    @Override
    public void writeReportToFile(String report, String filePath) {
        File reportFile = new File(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new InvalidDataException("Can't find file by path " + filePath);
        }
    }
}
