package core.basesyntax.store.report;

import core.basesyntax.store.Storage;
import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";

    @Override
    public String getReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(HEADER).append(System.lineSeparator());

        // Використовуємо TreeMap для сортування за ключами
        Map<String, Integer> sortedFruits = new TreeMap<>(Storage.getAllFruits());

        for (Map.Entry<String, Integer> entry : sortedFruits.entrySet()) {
            reportBuilder.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return reportBuilder.toString().trim();
    }
}
