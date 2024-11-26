package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    private static final String SEPARATOR = ",";
    private static final String HEADER = "Fruit,Quantity";

    private final FruitDB fruitDB;

    public ReportGenerator(FruitDB fruitDB) {
        this.fruitDB = fruitDB;
    }

    public List<String> generateReport() {
        List<String> report = new ArrayList<>();
        report.add(HEADER);
        List<String> sortedEntries = fruitDB.getInventory().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + SEPARATOR + entry.getValue())
                .collect(Collectors.toList());

        report.addAll(sortedEntries);
        return report;
    }
}
