package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.db.StorageImpl;
import java.util.List;

public interface ReportGenerator {
    List<String> generate(StorageImpl storage);
}
