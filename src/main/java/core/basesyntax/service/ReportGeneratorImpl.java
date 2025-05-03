package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String COMA_SEPARATOR = ",";
    private final String lineSeparator = System.lineSeparator();

    @Override
    public String getReport(ShopService shopService) {
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity" + lineSeparator);
        boolean firstEntry = true;
        for (Map.Entry<String, Integer> entry: shopService.getFruits().entrySet()) {
            if (!firstEntry) {
                stringBuilder.append(lineSeparator);
            }
            stringBuilder.append(entry.getKey())
                    .append(COMA_SEPARATOR)
                    .append(entry.getValue());
            firstEntry = false;
        }
        return stringBuilder.toString();
    }
}
