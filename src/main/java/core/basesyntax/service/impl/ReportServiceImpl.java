package core.basesyntax.service.impl;

import core.basesyntax.dao.impl.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String WORDS_SPLITERATOR = ",";
    private static final String END_LINE = System.lineSeparator();
    private static final String COLUMNS = "fruit,quantity";
    private final Map<String, Integer> fruits = Storage.fruits;

    @Override
    public String getReport() {
        if (fruits.isEmpty()) {
            throw new RuntimeException("Empty data!");
        }
        String report = COLUMNS + END_LINE;
        report += fruits.keySet().stream()
                .map(key -> key + WORDS_SPLITERATOR + fruits.get(key) + END_LINE)
                .sorted()
                .collect(Collectors.joining());
        return report.trim();
    }
}
