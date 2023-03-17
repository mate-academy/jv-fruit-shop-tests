package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStore;
import core.basesyntax.service.CreateReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateReportServiceImpl implements CreateReportService {
    private static final String TITLE = "fruit,quantity";
    private static final String SEPARATOR = ",";

    @Override
    public String generateReport() {
        Map<String, Integer> supplies = FruitStore.supplies;
        return supplies.entrySet()
                .stream()
                .map(entry -> entry.getKey()
                        + SEPARATOR
                        + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator(),
                        TITLE + System.lineSeparator(),
                        ""));
    }
}
