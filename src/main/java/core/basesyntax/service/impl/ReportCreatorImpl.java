package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String SPLITTER = ",";
    private static final String HEADER = "fruit,amount";

    @Override
    public String createReport() {
        if (Storage.fruits.isEmpty()) {
            throw new RuntimeException("Can`t create report from empty storage");
        }
        StringBuilder builder = new StringBuilder(HEADER);
        for (Map.Entry<String, Integer> element : Storage.fruits.entrySet()) {
            builder.append(System.lineSeparator())
                    .append(element.getKey())
                    .append(SPLITTER)
                    .append(element.getValue());
        }
        return builder.toString();
    }
}
