package core.basesyntax.service;

import java.util.Map;
import java.util.stream.Collectors;

public class ReportCreatorImpl implements ReportCreator {

    @Override
    public String createReport(Map<String, Integer> fruits) {
        if (fruits == null || fruits.isEmpty()) {
            throw new RuntimeException("Incoming Map = null or empty");
        }
        String fruitsReport = fruits.keySet().stream()
                .map(key -> key + "," + fruits.get(key))
                .collect(Collectors.joining(System.lineSeparator()));
        return "fruit,quantity" + System.lineSeparator() + fruitsReport;
    }
}
