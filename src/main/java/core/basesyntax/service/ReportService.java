package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import java.util.Map;

public class ReportService {
    private FruitDao fruitDao;

    public ReportService(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    public String generateReport() {
        if (Storage.fruits.isEmpty()) {
            throw new RuntimeException("Cannot generate report, storage is empty ");
        }
        StringBuilder report = new StringBuilder();
        report.append("type,fruit");
        for (Map.Entry<String, Integer> fruit : fruitDao.getFruits()) {
            report.append(System.lineSeparator() + fruit.getKey() + "," + fruit.getValue());
        }
        return report.toString();
    }
}
