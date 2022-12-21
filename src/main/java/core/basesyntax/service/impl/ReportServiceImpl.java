package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String INFORM_LINE = "fruit,quantity";

    @Override
    public String createReport(Map<Fruit, Integer> fruits) {
        if (fruits == null || fruits.containsKey(null) || fruits.containsValue(null)) {
            throw new RuntimeException("Wrong input data");
        }
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(INFORM_LINE);
        fruits.forEach((fruit, amount) -> reportBuilder.append(System.lineSeparator())
                .append(fruit.getName()).append(",").append(amount));
        return reportBuilder.toString();
    }

}
