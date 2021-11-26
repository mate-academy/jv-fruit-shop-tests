package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String TITLE = "fruit,quantity";
    private final FruitStorageDao fruitDao;

    public ReportCreatorImpl(FruitStorageDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public String makeReport() {
        StringBuilder report = new StringBuilder();
        report.append(TITLE).append(System.lineSeparator());
        for (Map.Entry<Fruit, Integer> entry : fruitDao.getAll().entrySet()) {
            report.append(entry.getKey().getName()).append(",").append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString().trim();
    }
}
