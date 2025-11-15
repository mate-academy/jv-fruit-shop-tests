package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearDown() {
        Storage.getAllItems().clear();
    }

    @Test
    void getReport_emptyMap_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected,actual);
    }

    @Test
    void getReport_gettingReport_ok() {
        Storage.add("banana", 300);
        Storage.add("apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,300" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected,actual);
    }
}
