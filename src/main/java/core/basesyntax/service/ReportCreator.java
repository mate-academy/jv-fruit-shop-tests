package core.basesyntax.service;

import core.basesyntax.db.Storage;

public class ReportCreator {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String COMMA = ",";

    public String createReport() {
        if (Storage.FRUITS.isEmpty()) {
            throw new RuntimeException("No data for the report!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FIRST_LINE).append(System.lineSeparator());
        for (String fruit : Storage.FRUITS.keySet()) {
            stringBuilder.append(fruit)
                    .append(COMMA)
                    .append(Storage.FRUITS.get(fruit))
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}
