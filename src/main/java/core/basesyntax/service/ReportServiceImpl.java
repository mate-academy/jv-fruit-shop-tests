package core.basesyntax.service;

import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String TITLE_OF_REPORT = "fruit,quantity";
    private static final String COMA = ",";

    @Override
    public String createReport(Map<String, Integer> map) {
        if (map.isEmpty() || map == null) {
            throw new RuntimeException("Invalid map " + map);
        }
        StringBuilder result = new StringBuilder();
        result.append(TITLE_OF_REPORT);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(COMA)
                    .append(entry.getValue());
        }
        return result.toString();
    }
}
