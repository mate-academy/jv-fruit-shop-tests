package core.basesyntax.service;

import core.basesyntax.storage.Storage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder("fruit,quantity" + System.lineSeparator());
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            report.append(entry.getKey()).append(",").append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
