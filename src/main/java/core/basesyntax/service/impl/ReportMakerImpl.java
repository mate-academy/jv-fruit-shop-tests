package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMaker;
import java.util.Map;

public class ReportMakerImpl implements ReportMaker {
    private static final String HEAD = "fruit,quantity";

    @Override
    public String createReport() {
        if (Storage.fruits.containsKey(null) || Storage.fruits.containsValue(null)) {
            throw new RuntimeException("Storage contains null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEAD);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey()).append(",").append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
