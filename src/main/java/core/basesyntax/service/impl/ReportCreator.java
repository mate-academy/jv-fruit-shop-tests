package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.getFruits;

import core.basesyntax.model.Fruit;

public class ReportCreator {
    public static final String REPORT_HEADER = "fruit,quantity";

    public static String createReport() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER).append(System.lineSeparator());
        for (Fruit fruit : getFruits()) {
            report.append(fruit.getName())
                    .append(",")
                    .append(fruit.getQuantity())
                    .append(System.lineSeparator());
        }
        if (report.toString().equals(REPORT_HEADER + System.lineSeparator())) {
            throw new IllegalArgumentException("There are no fruits in storage");
        }
        return report.toString();
    }
}
