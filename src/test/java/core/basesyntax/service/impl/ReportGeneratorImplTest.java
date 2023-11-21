package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void makeReport_ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        String report = reportGenerator.makeReport();
        Assertions.assertEquals(report,"banana, 152"
                + System.lineSeparator()
                + "apple, 90"
                + System.lineSeparator());
    }
}
