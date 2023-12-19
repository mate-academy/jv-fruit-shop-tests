package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDB;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String FIRST_LINE = "Fruit,Value";

    @Override
    public String makeReport() {
        if (FruitDB.FRUIT_DATA_BASE.isEmpty()) {
            return "";
        }
        StringBuilder report = new StringBuilder(FIRST_LINE).append(System.lineSeparator());
        int size = FruitDB.FRUIT_DATA_BASE.size();
        int count = 0;
        for (Map.Entry<String, Integer> entry : FruitDB.FRUIT_DATA_BASE.entrySet()) {
            report.append(entry.getKey())
                    .append(".")
                    .append(entry.getValue());
            count++;
            if (count < size) {
                report.append(System.lineSeparator());
            }
        }
        return report.toString();
    }
}
