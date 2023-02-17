package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String TITLE = "fruit,quantity\n";
    private static final String SEPARATOR = ",";
    private static final Map<String, Integer> STORAGE = Storage.fruits;

    @Override
    public String report() {
        String report = TITLE;
        report += STORAGE.keySet().stream()
                .map(s -> s + SEPARATOR + STORAGE.get(s) + System.lineSeparator())
                .collect(Collectors.joining());
        return report;
    }
}
