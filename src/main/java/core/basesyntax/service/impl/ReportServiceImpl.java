package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String EMPTY_STORAGE_MESSAGE = "Storage is empty";
    private final Map<String, Integer> storage;

    public ReportServiceImpl(Map<String, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public String getReport() {
        return storage.size() == 0 ? EMPTY_STORAGE_MESSAGE : buildReport();
    }

    private String buildReport() {
        return REPORT_HEADER + storage.entrySet().stream()
                .map(fruit -> LINE_SEPARATOR + fruit.getKey()
                        + COLUMN_SEPARATOR + fruit.getValue())
                .sorted()
                .collect(Collectors.joining());
    }
}
