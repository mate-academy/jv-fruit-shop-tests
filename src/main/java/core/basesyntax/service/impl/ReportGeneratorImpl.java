package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportCreator {
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITOR = ",";

    @Override
    public List<String> createReport(Map<String, Integer> data) {
        List<String> report = new ArrayList<>();
        report.add(HEADER);
        for (Map.Entry<String, Integer> entry : Storage.fruitsStorage.entrySet()) {
            report.add(entry.getKey() + DELIMITOR + entry.getValue());
        }
        return report;
    }
}
