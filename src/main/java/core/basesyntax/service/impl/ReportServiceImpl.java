package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    @Override
    public String getReport(Map<String, Integer> storageFruits) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("fruit,quantity").append(System.lineSeparator());
        for (Map.Entry<String, Integer> map : storageFruits.entrySet()) {
            reportBuilder.append(map.getKey())
                    .append(",")
                    .append(map.getValue())
                    .append(System.lineSeparator());
        }
        return reportBuilder.toString();
    }
}
