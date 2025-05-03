package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    @Override
    public String makeReport() {
        return Storage.fruits.entrySet().stream()
                .map(e -> e.getKey() + "," + e.getValue() + System.lineSeparator())
                .collect(Collectors.joining());
    }
}
