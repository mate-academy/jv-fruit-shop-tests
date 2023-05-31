package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    @Override
    public String getReport(List<Fruit> fruits) {
        if (fruits == null || fruits.isEmpty()) {
            throw new RuntimeException("Wrong or empty data came to report");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit").append(",").append("quantity")
                .append(System.lineSeparator());
        for (Fruit item : fruits) {
            stringBuilder.append(item.getFruitName()).append(",")
                    .append(item.getQuantity()).append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}
