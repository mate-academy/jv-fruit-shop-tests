package fruitshop.service.impl;

import fruitshop.db.Storage;
import fruitshop.service.Report;
import java.util.Map;

public class ReportImpl implements Report {
    private static final String SEPARATOR = ",";
    private static final String HEADER = "fruit,quantity";

    @Override
    public String create() {
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder
                .append(HEADER)
                .append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : Storage.getFruitsAndAmount().entrySet()) {
            reportBuilder
                    .append(entry.getKey())
                    .append(SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return reportBuilder.toString();
    }
}
