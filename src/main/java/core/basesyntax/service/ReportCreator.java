package core.basesyntax.service;

import core.basesyntax.db.Storage;
import java.util.List;

public interface ReportCreator {
    List<String[]> getReport(Storage outerStorage);

    void reportFlush();
}
