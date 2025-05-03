package core.basesyntax.data.servise.ipl;

import core.basesyntax.data.db.Storage;
import core.basesyntax.data.servise.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMMA = ",";
    private static final String HEADER = "fruit,quantity";

    @Override
    public String getReport() {
        StringBuilder stringBuilder = new StringBuilder(HEADER + LINE_SEPARATOR);
        for (Map.Entry<String, Integer> entry : Storage.fruitsStorage.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }
}
