package core.basesyntax.service;

import core.basesyntax.storage.Storage;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder("fruit,quantity" + System.lineSeparator());
        Storage.getFruits().forEach((fruit, quantity) -> report.append(fruit)
                .append(",").append(quantity).append(System.lineSeparator()));
        return report.toString();
    }
}
