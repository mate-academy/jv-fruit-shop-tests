package core.basesyntax.util;

import core.basesyntax.db.Storage;

public interface ReportGenerator {
    String generate(Storage storage);
}
