package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.getFruits;

import core.basesyntax.model.Fruit;

public class ReportCreator {
    private static final String REPORT_HEADER = "fruit,quantity";

    public static String getReportHeader() {
        return REPORT_HEADER;
    }

    public static String createReport() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER).append(System.lineSeparator());
        for (Fruit fruit : getFruits()) {
            report.append(fruit.getName())
                    .append(",")
                    .append(fruit.getQuantity())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
