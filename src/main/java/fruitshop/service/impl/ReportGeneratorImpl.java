package fruitshop.service.impl;

import fruitshop.db.Storage;
import fruitshop.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String generate() {
        StringBuilder report = new StringBuilder("fruit,quantity\n");

        Storage.fruits.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(entry.getKey())
                        .append(",")
                        .append(entry.getValue())
                        .append("\n"));

        return report.toString();
    }
}
