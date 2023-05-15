package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    @Override
    public String createReport() {
        return FruitStorage.storage.entrySet().stream()
                .map(fs -> fs.getKey() + "," + fs.getValue())
                .collect(Collectors.joining(System.lineSeparator(),
                        "fruit,quantity" + System.lineSeparator(), ""));
    }
}
