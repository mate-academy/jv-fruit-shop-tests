package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGeneratingService;
import java.util.ArrayList;
import java.util.List;

public class ReportGeneratingServiceImpl implements ReportGeneratingService {
    private static final String FIRST_LINE_OF_REPORT = "fruit,quantity";
    private static final String CSV_SEPARATOR = ",";

    @Override
    public List<String> createReport(List<Fruit> db) {
        List<String> report = new ArrayList<>();
        report.add(FIRST_LINE_OF_REPORT);
        for (Fruit fruit : db) {
            report.add(System.lineSeparator() + fruit.getFruitType()
                    + CSV_SEPARATOR + fruit.getQuantity());
        }
        return report;
    }
}
