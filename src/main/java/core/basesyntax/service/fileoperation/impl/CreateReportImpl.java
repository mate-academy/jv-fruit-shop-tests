package core.basesyntax.service.fileoperation.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.fileoperation.CreateReport;

public class CreateReportImpl implements CreateReport {
    private static final String TITLE = "fruit,quantity";
    private final StringBuilder builder = new StringBuilder();
    private final StorageDao storageDao;

    public CreateReportImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String getReport() {
        if (storageDao == null) {
            throw new RuntimeException("Storage dao is null ");
        }
        builder.append(TITLE);
        builder.append(System.lineSeparator());
        for (Fruit fruit: storageDao.getAll()) {
            builder.append(fruit.getName());
            builder.append(",");
            builder.append(fruit.getAmountFruit());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
