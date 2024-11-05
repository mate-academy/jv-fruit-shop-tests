package core.basesyntax.service;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private final String comma = ",";
    private final String lineSeparator = System.lineSeparator();

    @Override
    public String getReport(ShopService shopService) {
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity" + lineSeparator);
        for (Map.Entry<String, Integer> entry: shopService.getFruits().entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(comma)
                    .append(entry.getValue())
                    .append(lineSeparator);
        }
        return stringBuilder.toString();
    }
}
