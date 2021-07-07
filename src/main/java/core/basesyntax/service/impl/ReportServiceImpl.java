package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String REGEX = ",";

    @Override
    public String makeReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER);
        for (Map.Entry<Fruit, Integer> entry : Storage.storage.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey().getName())
                    .append(REGEX)
                    .append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
