package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private StorageDao storageDao;

    public ReportServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String report() {
        return new StringBuilder("fruit,quantity")
                .append(storageDao.getAll().stream()
                        .map(e -> new StringBuilder("\n")
                        .append(e.getKey().getName())
                        .append(",")
                        .append(e.getValue()))
                        .collect(Collectors.joining()))
                .toString();
    }
}
