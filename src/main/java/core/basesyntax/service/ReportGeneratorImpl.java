package core.basesyntax.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String DEFAULT_FIRST_LINE = "fruit,quantity" + System.lineSeparator();
    private static final char SEPARATOR = ',';

    @Override
    public String getReport(Map<String, Integer> fruitTransactionList) {
        return DEFAULT_FIRST_LINE
                + fruitTransactionList.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
