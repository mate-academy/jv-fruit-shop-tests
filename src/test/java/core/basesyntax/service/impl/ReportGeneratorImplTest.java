package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private static ReportGenerator reportGenerator;
    private static final String BANANA_NAME = "banana";
    private static final String APPLE_NAME = "apple";

    @BeforeAll
    static void init() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void makeReport_ok() {
        Storage.storage.put(BANANA_NAME, 152);
        Storage.storage.put(APPLE_NAME, 90);
        String report = reportGenerator.makeReport();
        Assertions.assertEquals(report,BANANA_NAME + ", 152"
                + System.lineSeparator()
                + APPLE_NAME + ", 90"
                + System.lineSeparator());
    }
}
