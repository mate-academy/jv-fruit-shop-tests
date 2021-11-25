package core.service.impl;

import core.model.Fruit;
import core.service.CreateReport;
import java.util.List;

public class CreateReportImpl implements CreateReport {
    private static final String TITLE_TEXT = "fruit,quantity";

    public String createReport(List<Fruit> fruits) {
        StringBuilder builder = new StringBuilder();
        builder.append(TITLE_TEXT).append(System.lineSeparator());
        for (Fruit fruit : fruits) {
            builder.append(fruit.getName()).append(",")
                    .append(fruit.getQuantity())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
