package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.CreatorReportService;
import java.util.stream.Collectors;

public class CreatorReportServiceImpl implements CreatorReportService {
    private static final String FIRST_ROW = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private final StorageDao storageDao;

    public CreatorReportServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String createReport() {
        return FIRST_ROW + storageDao.getStorage()
                .entrySet()
                .stream()
                .map(s -> System.lineSeparator()
                        + s.getKey()
                        + SEPARATOR
                        + s.getValue())
                .collect(Collectors.joining());
    }
}
