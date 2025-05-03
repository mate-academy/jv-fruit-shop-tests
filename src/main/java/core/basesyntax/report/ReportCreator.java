package core.basesyntax.report;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

public class ReportCreator {
    private static final String REPORT_HEADER = "fruit,quantity";

    public String createReport() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER).append(System.lineSeparator());
        if (fruitStorage.isEmpty()) {
            throw new IllegalArgumentException("Can't create report, storage is empty");
        }
        fruitStorage.forEach((key, value) -> {
            report.append(key)
                    .append(",")
                    .append(value)
                    .append(System.lineSeparator());
        });
        return report.toString();
    }
}
