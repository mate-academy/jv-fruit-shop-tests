package core.basesyntax.service.impl;

import core.basesyntax.service.ReportMakerService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportMakerServiceImpl implements ReportMakerService {

    private static final String HEAD_LINER = "fruit, quantity";

    @Override
    public String report(Map<String, Integer> report) {
        if (report.keySet().size() < 1) {
            throw new RuntimeException("Input is empty, nothing to do here");
        }
        return HEAD_LINER + report
                .entrySet()
                .stream()
                .map(e -> System.lineSeparator() + e.getKey() + ", " + e.getValue())
                .collect(Collectors.joining());
    }
}
