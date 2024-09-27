package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import java.util.Map;
import java.util.Set;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final char SEPARATOR = ',';

    @Override
    public String getReport(ShopService service) {
        Map<String, Integer> fruitMap = service.getStorage();
        if (fruitMap.isEmpty()) {
            return "";
        }
        Set<String> fruits = fruitMap.keySet();
        StringBuilder sb = new StringBuilder();
        Integer quantity;
        for (String fruit : fruits) {
            quantity = fruitMap.get(fruit);
            if (quantity == null) {
                throw new IllegalArgumentException(
                        "For some reason the quantity of fruit "
                                + fruit + "was set as null in the storage");
            }
            sb.append(System.lineSeparator())
                    .append(fruit)
                    .append(SEPARATOR)
                    .append(" ")
                    .append(quantity);
        }
        return sb.substring(1);
    }
}
