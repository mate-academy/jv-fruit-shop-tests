package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator rg;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storage.put("banana", 2);
        storage.put("apple", 1);
        rg = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_shouldReturnExpectedCsv() {
        String expected = String.join(System.lineSeparator(),
                "fruit,quantity",
                "banana,2",
                "apple,1"
        ) + System.lineSeparator();

        String report = rg.getReport();

        assertEquals(expected, report);
    }
}
