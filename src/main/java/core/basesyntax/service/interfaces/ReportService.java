package core.basesyntax.service.interfaces;

import core.basesyntax.db.Storage;
import java.util.List;

public interface ReportService {
    List<String> generateReport(Storage storage);
}
