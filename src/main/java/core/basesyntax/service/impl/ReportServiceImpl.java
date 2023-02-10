package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String TITLE = "fruit,quantity";
    private static final String SEPARATOR = ",";

    @Override
    public String report() {
        String report = TITLE;
        report += FruitStorage.fruits.keySet().stream()
                .map(k -> System.lineSeparator() + k + SEPARATOR + FruitStorage.fruits.get(k))
                .collect(Collectors.joining());
        return report;
    }
}
