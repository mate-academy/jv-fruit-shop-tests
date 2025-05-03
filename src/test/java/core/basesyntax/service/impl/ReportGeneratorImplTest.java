package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Storage storage;
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_generateCorrectReport_ok() {
        storage.addEntry("banana", 20);
        storage.addEntry("apple", 100);
        storage.addEntry("orange", 5);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "orange,5" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport);
    }
}
