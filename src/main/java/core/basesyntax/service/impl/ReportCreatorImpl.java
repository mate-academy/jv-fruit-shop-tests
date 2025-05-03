package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.StorageException;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String HEADLINE = "fruit,quantity";
    private static final int HEADLINE_INDEX = 0;
    private static final int FIRST_LINE_REPORT = 1;

    @Override
    public String[] getReport() {
        checkStorage();
        String[] report = new String[Storage.fruits.size() + 1];
        report[HEADLINE_INDEX] = HEADLINE;
        int counter = FIRST_LINE_REPORT;
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            report[counter++] = entry.getKey() + "," + entry.getValue();
        }
        return report;
    }

    private void checkStorage() {
        if (Storage.fruits.size() == 0) {
            throw new StorageException("Storage is empty");
        }
    }
}
