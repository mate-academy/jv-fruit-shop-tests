package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.getFruitStorage;

import core.basesyntax.db.Storage;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    @Override
    public String createReport() {
        if (Storage.getFruitStorage().isEmpty()) {
            return "Storage is empty.";
        }
        StringBuilder report = new StringBuilder("fruit, quantity" + System.lineSeparator());
        for (Map.Entry<String, Integer> e : getFruitStorage().entrySet()) {
            report.append(e.getKey())
                    .append(", ")
                    .append(e.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
