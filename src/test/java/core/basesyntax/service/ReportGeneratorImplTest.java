package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private FruitStorage storage;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_fruitsInStorage_ok() {
        storage.setQuantity("banana", 152);
        storage.setQuantity("apple", 90);

        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();

        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator();

        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }
}
