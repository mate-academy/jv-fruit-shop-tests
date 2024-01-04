package core.basesyntax.service.transaction;

import core.basesyntax.db.Storage;
import java.util.stream.Collectors;

public class ReportListFruitImpl implements ReportListFruit {
    private static final String DELIMITER = ",";
    private static final String FILE_HEADER = "fruit,quantity";

    @Override
    public String createReport() {
        StringBuilder builder = new StringBuilder();
        builder.append(FILE_HEADER).append(System.lineSeparator())
                .append(Storage.fruitsDB.entrySet().stream()
                        .map(key -> key.getKey() + DELIMITER + key.getValue())
                        .collect(Collectors.joining(System.lineSeparator())));
        return builder.toString();
    }
}
