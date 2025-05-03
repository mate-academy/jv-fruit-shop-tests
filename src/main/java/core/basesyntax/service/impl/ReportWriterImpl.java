package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriterImpl implements ReportWriter {

    @Override
    public void reportWriter(String pathName, String reportRecords) {
        final File toFile = new File(pathName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile))) {
            bufferedWriter.write(reportRecords);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to: " + pathName, e);
        }
    }
}
