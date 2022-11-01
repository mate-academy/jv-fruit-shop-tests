package core.basesyntax.service.write.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.write.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String CSV_SEPARATOR = ",";
    private static final String START_MESSAGE = "fruit,quantity";
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public String createReport() {
        StringBuilder report = new StringBuilder();
        report.append(START_MESSAGE);
        storageDao.getAll().stream()
                .map(Map.Entry::getKey)
                .sorted()
                .forEach(e -> report.append(LINE_SEPARATOR)
                        .append(e)
                        .append(CSV_SEPARATOR)
                        .append(storageDao.getQuantity(e)));
        return report.toString();
    }
}
