package core.basesyntax.service.implementation;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerationService;
import java.util.Map;

public class ReportGenerationServiceImpl implements ReportGenerationService {
    private static final String COMMA = ",";
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = System.lineSeparator();

    @Override
    public String generate() {
        StringBuilder report = new StringBuilder();
        report.append(HEADER).append(SEPARATOR);
        for (Map.Entry<String, Integer> entry : Storage.STORAGE.entrySet()) {
            report.append(entry.getKey())
                    .append(COMMA)
                    .append(getValidValue(entry))
                    .append(SEPARATOR);
        }
        return report.toString();
    }

    private int getValidValue(Map.Entry<String, Integer> entry) {
        if (entry.getValue() < 0) {
            throw new RuntimeException("Invalid data in the report. "
                    + "Product quantity can't be negative");
        }
        return entry.getValue();
    }
}
