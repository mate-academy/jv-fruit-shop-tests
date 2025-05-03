package core.basesyntax.service;

import core.basesyntax.db.Storage;

public class ReportCreator {
    private static final String DELIMITER = ",";

    public String createReport(Storage storage) {
        StringBuilder report = new StringBuilder("fruit,quantity" + System.lineSeparator());
        int index = 0;
        int size;
        try {
            size = storage.getFruits().size();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Storage is null!", e);
        }
        if (storage.getFruits().keySet().isEmpty()) {
            throw new IllegalArgumentException("Storage is empty!");
        }
        for (String key : storage.getFruits().keySet()) {
            if (storage.getFruits().get(key) == 0) {
                index++;
                continue;
            }
            report.append(key + DELIMITER + storage.getFruits().get(key));
            if (++index < size) {
                report.append(System.lineSeparator());
            }
        }
        return report.toString();
    }
}
