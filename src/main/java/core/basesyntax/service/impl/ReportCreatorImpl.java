package core.basesyntax.service.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String TITLE = "fruit,quantity";

    @Override
    public String createReport() {
        StringBuilder builder = new StringBuilder(TITLE + System.lineSeparator());
        for (Map.Entry<String, Integer> fruitTransaction : Storage.STORAGE.entrySet()) {
            builder.append(String.format("%s,%s%s", fruitTransaction.getKey(),
                    fruitTransaction.getValue(), System.lineSeparator()));
        }
        return builder.toString();
    }
}
