package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    @Override
    public void createReportFile(String fruitBalanceReport, String filePath) {
        if (filePath == null) {
            throw new RuntimeException("Please, enter path of the file!");
        }
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(fruitBalanceReport);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report to the file by path: "
                    + filePath, e);
        }
    }
}
