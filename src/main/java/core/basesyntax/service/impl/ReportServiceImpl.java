package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private final StorageDao storageDao;

    public ReportServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String report() {
        return "fruit,quantity\n" + storageDao.getAll().stream()
                .filter(i -> i.getKey() != null && i.getValue() != null)
                .map(i -> i.getKey().getFruit()
                        + "," + i.getValue()
                        + System.lineSeparator())
                .collect(Collectors.joining());
    }
}
