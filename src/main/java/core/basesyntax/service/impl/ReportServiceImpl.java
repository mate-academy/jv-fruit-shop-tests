package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.service.ReportService;
import java.util.StringJoiner;

public class ReportServiceImpl implements ReportService {
    private static final Storage storage = new StorageImpl();
    private static final String TITLE = "fruit,quantity\n";

    @Override
    public String generateReport() {
        StringJoiner joiner = new StringJoiner(",\n");
        for (String fruit : storage.getKeys()) {
            joiner.add(fruit + "=" + storage.getValue(fruit));
        }
        return TITLE + joiner;
    }
}
