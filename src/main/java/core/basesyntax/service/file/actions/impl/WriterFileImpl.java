package core.basesyntax.service.file.actions.impl;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.service.file.actions.WriterFile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriterFileImpl implements WriterFile {
    private static final String TITLE_REPORT = "fruit,quantity" + System.lineSeparator();
    private static final String SPLIT_FIELDS = ",";

    @Override
    public void writeReportFruitShop(FruitTransactionDb fruitStoreDB, String pathFileReport) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileReport))) {
            writer.write(TITLE_REPORT);

            for (Map.Entry<String, Integer> entry : fruitStoreDB.getAllFruitsTransaction()) {
                StringBuilder report = new StringBuilder();
                report.append(entry.getKey()).append(SPLIT_FIELDS).append(entry.getValue())
                        .append(System.lineSeparator());
                writer.write(report.toString());
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to write information from this file "
                    + pathFileReport);
        }
    }
}
