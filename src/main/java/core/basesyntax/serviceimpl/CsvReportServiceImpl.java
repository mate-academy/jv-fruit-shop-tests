package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class CsvReportServiceImpl implements ReportService {
    private static final String FIRST_LINE = "fruit,quantity";

    @Override
    public String createOurReport() {
        return FIRST_LINE + Storage.fruitsStorage.entrySet().stream()
                .map(entry -> "\n" + entry.getKey() + "," + entry.getValue())
                .collect(Collectors.joining());
    }
}
