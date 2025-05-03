package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private static final String HEADER = "Fruit,Quantity";
    private static final String SEPARATOR = ",";

    public List<String> generateReport() {
        Map<String, Integer> inventory = FruitDB.getInventory();
        List<String> report = new ArrayList<>();
        report.add(HEADER);

        inventory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.add(entry.getKey() + SEPARATOR + entry.getValue()));

        return report;
    }
}
