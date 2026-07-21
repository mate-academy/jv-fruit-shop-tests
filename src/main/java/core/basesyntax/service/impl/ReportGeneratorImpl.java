package core.basesyntax.service.impl;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder(HEADER)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : ShopStorage.storage.entrySet()) {
            sb.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
