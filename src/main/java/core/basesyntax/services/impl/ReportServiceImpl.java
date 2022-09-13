package core.basesyntax.services.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.services.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private final FruitStorageDao fruitStorageDao;

    public ReportServiceImpl(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public String createReport() {
        if (fruitStorageDao.getDataFromStorage().isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        StringBuilder report = new StringBuilder("fruit,quantity");
        for (Map.Entry<String, Integer> entry : fruitStorageDao.getDataFromStorage().entrySet()) {
            report.append(System.lineSeparator()).append(entry.getKey())
                    .append(",").append(entry.getValue());
        }
        return report.toString();
    }
}
