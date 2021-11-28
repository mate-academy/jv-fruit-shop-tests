package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.Set;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_TITLE = "fruit,quantity";
    private static final String COMA_SEPARATOR = ",";

    @Override
    public String formReport(Set<Map.Entry<String, Integer>> elements) {
        StringBuilder reportCreator = new StringBuilder(REPORT_TITLE);
        for (Map.Entry<String, Integer> element : elements) {
            reportCreator.append(System.lineSeparator())
                    .append(element.getKey())
                    .append(COMA_SEPARATOR)
                    .append(element.getValue());
        }
        return reportCreator.toString();
    }
}
