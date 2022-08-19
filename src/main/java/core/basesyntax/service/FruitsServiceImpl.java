package core.basesyntax.service;

import java.util.Map;

public class FruitsServiceImpl implements FruitsService {
    private static final String TITLE_FOR_REPORT = "fruit,quantity";

    @Override
    public String generateFruitsReport(Map<String, Integer> fruits) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(TITLE_FOR_REPORT);
        if (!fruits.isEmpty()) {
            for (Map.Entry<String, Integer> fruit : fruits.entrySet()) {
                reportBuilder.append(System.lineSeparator()).append(fruit.getKey())
                        .append(",").append(fruit.getValue());
            }
        }
        return reportBuilder.toString();
    }
}
