package core.basesyntax.service;

import core.basesyntax.dao.InputDao;
import core.basesyntax.dao.InputDaoImpl;
import core.basesyntax.model.Fruit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriterImpl implements CsvFileWriter {
    private static final String TITLE_LINE_OF_REPORT_FILE = "fruit,quantity";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @Override
    public void createReportFile(String reportFilePath) {
        InputDao inputDao = new InputDaoImpl();
        File file = new File(reportFilePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            StringBuilder builder = new StringBuilder(TITLE_LINE_OF_REPORT_FILE);
            Fruit banana = inputDao.getFruit(BANANA);
            Fruit apple = inputDao.getFruit(APPLE);
            builder.append(System.lineSeparator())
                    .append(banana.getFruitName() + "," + banana.getFruitQuantity())
                    .append(System.lineSeparator())
                    .append(apple.getFruitName() + "," + apple.getFruitQuantity());
            bufferedWriter.write(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }
}
