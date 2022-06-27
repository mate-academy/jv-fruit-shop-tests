package basesyntax.service.impl;

import basesyntax.dao.StorageDao;
import basesyntax.service.ReportCreateService;
import java.util.stream.Collectors;

public class ReportCreateServiceImpl implements ReportCreateService {
    private static final String SPLITERATOR = ",";
    private static final String TITLE = "fruit,quantity";
    private final StorageDao storageDao;

    public ReportCreateServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String createReport() {
        return TITLE + storageDao.getStorage()
                .entrySet()
                .stream()
                .map(e -> System.lineSeparator()
                        + e.getKey()
                        + SPLITERATOR
                        + e.getValue())
                .collect(Collectors.joining());
    }
}
