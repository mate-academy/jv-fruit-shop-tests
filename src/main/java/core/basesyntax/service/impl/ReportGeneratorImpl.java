package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final char SEPARATOR = ',';

    @Override
    public String getReport(ShopService service) {
        Map<String, Integer> fruitMap = service.getStorage();
        if (fruitMap.isEmpty()) {
            return "";
        }
        List<String> fruits = fruitMap
                .keySet()
                .stream()
                .toList();
        StringBuilder sb = new StringBuilder();
        Integer quantity;
        String fruit;
        for (int i = 0; i < fruits.size(); ++i) {
            fruit = fruits.get(i);
            quantity = fruitMap.get(fruits.get(i));
            if (quantity == null) {
                throw new IllegalArgumentException(
                        "For some reason the quantity of fruit "
                                + fruit + "was set as null in the storage");
            }
            sb.append(fruit)
                    .append(SEPARATOR)
                    .append(" ")
                    .append(quantity);
            if (i != fruits.size() - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
