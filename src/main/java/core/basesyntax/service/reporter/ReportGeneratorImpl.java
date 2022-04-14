package core.basesyntax.service.reporter;

import core.basesyntax.model.Fruit;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMA_SEPARATOR = ",";
    private static final String FIRST_LINE_IN_REPORT = "fruit,quantity" + LINE_SEPARATOR;

    public String createReport(Map<Fruit, Integer> storage) {
        StringBuffer report = new StringBuffer(FIRST_LINE_IN_REPORT);
        for (Map.Entry<Fruit, Integer> fruitIntegerEntry
                : storage.entrySet()) {
            report.append(fruitIntegerEntry.getKey().getName()).append(COMA_SEPARATOR)
                    .append(fruitIntegerEntry.getValue())
                    .append(LINE_SEPARATOR);
        }
        return report.toString();
    }
}
