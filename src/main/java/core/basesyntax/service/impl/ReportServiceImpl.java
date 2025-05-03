package core.basesyntax.service.impl;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final Map<String, Integer> STORAGE = ShopStorage.fruitsStorage;
    private static final String END_LINE = System.lineSeparator();
    private static final String COLUMNS = "fruit,quantity" + END_LINE;
    private static final String LINE_SPLITERATOR = ",";

    @Override
    public String getReport() {
        if (STORAGE.isEmpty()) {
            throw new RuntimeException("Empty data!");
        }
        String report = COLUMNS;
        report += STORAGE.keySet().stream()
                .map(key -> key + LINE_SPLITERATOR + STORAGE.get(key) + END_LINE)
                .collect(Collectors.joining());
        return report;
    }
}
