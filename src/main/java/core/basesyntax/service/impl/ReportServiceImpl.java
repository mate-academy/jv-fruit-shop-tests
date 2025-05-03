package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String TITLE = "fruit,quantity";

    @Override
    public String getReport() {
        StringBuilder stringBuilder = new StringBuilder(TITLE);
        if (Storage.getDataBase().isEmpty()) {
            return stringBuilder.toString();
        }
        return stringBuilder.append(System.lineSeparator())
                .append(Storage.getDataBase()
                        .entrySet()
                        .stream()
                        .map(e -> e.getKey().getFruitName()
                                + "," + e.getValue() + System.lineSeparator())
                        .collect(Collectors.joining()))
                .toString();

    }
}
