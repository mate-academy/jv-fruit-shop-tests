package core.basesyntax.service.impl;

import core.basesyntax.service.ReportMessageCreator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportWriterImpl implements core.basesyntax.service.ReportWriter {
    private final ReportMessageCreator createReportMessage = new ReportMessageCreatorImpl();

    @Override
    public void write(Map<String, Integer> toWrite, String fileName) {
        if (toWrite == null || toWrite.isEmpty() || fileName.isEmpty()) {
            throw new RuntimeException("Map what is used to write the file is null!");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(createReportMessage.createMessage(toWrite));
        } catch (IOException e) {
            throw new RuntimeException("Can't write information to the file" + fileName);
        }
    }
}
