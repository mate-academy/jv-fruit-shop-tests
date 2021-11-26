package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String TEXT_TITLE = "fruit,quantity";

    @Override
    public List<String> createReport() {
        List<String> report = new ArrayList<>();
        report.add(TEXT_TITLE);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Fruit, Integer> entry : Storage.storage.entrySet()) {
            stringBuilder.append(entry.getKey().getName()).append(",").append(entry.getValue());
            report.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return report;
    }
}
