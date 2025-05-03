package core.basesyntax.service.impl;

import core.basesyntax.model.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    private static ReportGenerator reportGenerator;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void getReport_correctReport_ok() {
        storage.put("banana", 152);
        storage.put("apple", 90);
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(REPORT, actual);
    }

    @Test
    void getReport_emptyStorage_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> reportGenerator.getReport(),
                "Expected RuntimeException was not thrown in " + ReportGeneratorImpl.class);
    }
}
