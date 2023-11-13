package core.basesyntax.reporter;

import core.basesyntax.dao.StorageDao;
import java.util.Map;

public class CsvReportGenerator implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String EMPTY_STORAGE_MESSAGE = "Empty storage: ";
    private final StorageDao storageDao;

    public CsvReportGenerator(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public String createReport() {
        Map<String, Integer> storage = storageDao.getStorage();
        if (storage.isEmpty()) {
            throw new RuntimeException(EMPTY_STORAGE_MESSAGE + storage);
        }
        StringBuilder report = new StringBuilder();
        report.append(HEADER).append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            report.append(entry.getKey()).append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
