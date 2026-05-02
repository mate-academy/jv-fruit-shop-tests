package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.service.ReportGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public List<String> generate(StorageImpl storage) {
        List<String> report = new ArrayList<>();
        report.add(HEADER);

        for (Map.Entry<String, Integer> entry : storage.getAll().entrySet()) {
            report.add(entry.getKey() + COMMA + entry.getValue());
        }
        return report;
    }
}
