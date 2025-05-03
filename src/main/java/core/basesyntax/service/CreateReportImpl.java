package core.basesyntax.service;

import core.basesyntax.storage.DataStorage;
import java.util.Map;

public class CreateReportImpl implements CreateReport {
    private static final String COMA_SEPARATOR = ",";
    private static final String TITLE = "fruit,quantity";

    private StringBuilder reportBuilder = new StringBuilder(TITLE);

    public String report() {
        for (Map.Entry<String, Integer> m: DataStorage.fruitMap.entrySet()) {
            if (m.getKey().isEmpty() || m.getValue() == null) {
                throw new RuntimeException("Incorrect data!!");
            }
            reportBuilder.append(System.lineSeparator()).append(m.getKey())
                    .append(COMA_SEPARATOR).append(m.getValue());
        }
        return reportBuilder.toString();
    }
}
