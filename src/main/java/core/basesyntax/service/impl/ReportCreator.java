package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.service.CreatReport;
import java.util.List;

public class ReportCreator implements CreatReport {
    private final FruitStorageDao storageDao;

    public ReportCreator(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public List<String> creatReport() {
        return storageDao.getAll();
    }
}
