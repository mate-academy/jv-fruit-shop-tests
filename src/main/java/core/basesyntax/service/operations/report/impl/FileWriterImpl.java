package core.basesyntax.service.operations.report.impl;

import core.basesyntax.service.operations.report.FileWriter;
import exception.CustomException;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    public void writeDataToFile(String reportContent, String filePath) {
        if (reportContent == null || filePath == null) {
            throw new CustomException("Report content or file path is null");
        }

        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            writer.write(reportContent);
        } catch (IOException e) {
            throw new CustomException("Failed to write report to file", e);
        }
    }
}
