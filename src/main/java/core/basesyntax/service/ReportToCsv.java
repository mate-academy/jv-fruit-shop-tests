package core.basesyntax.service;

import core.basesyntax.db.StockDao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportToCsv implements Report {
    private final String fileName;
    private final StockDao stockDao;

    public ReportToCsv(StockDao stockDao, String fileName) {
        this.stockDao = stockDao;
        this.fileName = fileName;
    }

    @Override
    public void prepare() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String product : stockDao.getProductsList()) {
                writer.write(product + "," + stockDao.get(product) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cant print to file", e);
        }
    }
}
