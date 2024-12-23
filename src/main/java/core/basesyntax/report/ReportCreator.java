package core.basesyntax.report;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

public class ReportCreator {
    private static final String REPORT_HEADER = "fruit,quantity";

    public String createReport() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER).append(System.lineSeparator());
        fruitStorage.forEach((key, value) -> {
            if (key == null || value == null) {
                throw new IllegalArgumentException("Data cannot contain null");
            }
            report.append(key)
                    .append(",")
                    .append(value)
                    .append(System.lineSeparator());
        });
        return report.toString();
    }
}
