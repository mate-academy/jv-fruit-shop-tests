package core.basesyntax.serviceimpl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REGEX_TO_SPLIT = ",";
    private static final String CONTENTS = "fruit, quantity";

    @Override
    public String createReport() {
        StringBuilder stringBuilder = new StringBuilder(CONTENTS);
        for (Map.Entry<String, Integer> entry : FruitStorage.getFruits().entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(REGEX_TO_SPLIT)
                    .append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
