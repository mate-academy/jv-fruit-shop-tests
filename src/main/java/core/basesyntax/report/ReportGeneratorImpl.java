package core.basesyntax.report;

import core.basesyntax.storage.Storage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder("fruit,quantity\n");
        Storage.getFruitStorage().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(entry.getKey())
                        .append(",")
                        .append(entry.getValue())
                        .append("\n"));

        return report.toString();
    }
}
