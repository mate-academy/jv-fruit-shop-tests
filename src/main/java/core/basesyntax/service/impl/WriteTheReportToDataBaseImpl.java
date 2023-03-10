package core.basesyntax.service.impl;

import core.basesyntax.service.CreateReportMessage;
import core.basesyntax.service.WriteTheReportToDataBase;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteTheReportToDataBaseImpl implements WriteTheReportToDataBase {
    private final CreateReportMessage createReportMessage = new CreateReportMessageImpl();

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
