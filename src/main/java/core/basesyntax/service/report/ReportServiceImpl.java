package core.basesyntax.service.report;

import core.basesyntax.dao.FruitStorageDao;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_TITLE = "fruit,quantity";
    private FruitStorageDao storageDao;

    public ReportServiceImpl(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String getReport() {
        return REPORT_TITLE
                + System.lineSeparator()
                + storageDao
                .getAllFruitsNames()
                .stream()
                .filter(Objects::nonNull)
                .map(c -> c + "," + storageDao.get(c))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
