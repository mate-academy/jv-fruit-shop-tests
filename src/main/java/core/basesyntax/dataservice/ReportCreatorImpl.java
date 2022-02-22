package core.basesyntax.dataservice;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String COLUMN_NAMES = "fruit,quantity";

    @Override
    public List<String> createReport() {
        if (Storage.storage.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        if (Storage.storage.containsKey(null)) {
            throw new RuntimeException("Storage can't contain null.");
        }
        List<String> report = new ArrayList<>();
        report.add(COLUMN_NAMES);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Fruit, Integer> entry : Storage.storage.entrySet()) {
            builder.append(entry.getKey().getNameFruit()).append(",").append(entry.getValue());
            report.add(builder.toString());
            builder.setLength(0);
        }
        return report;
    }
}
