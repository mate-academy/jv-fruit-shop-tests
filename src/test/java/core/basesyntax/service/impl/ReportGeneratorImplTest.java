package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    public void setUp() {
        Storage.clear();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void getReport_emptyStorage_returnsHeader() {
        assertEquals("fruit,quantity", reportGenerator.getReport());
    }

    @Test
    public void getReport_filledStorage_returnsCsvReport() {
        Storage.put("banana", 20);
        Storage.put("apple", 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,10";

        assertEquals(expected, reportGenerator.getReport());
    }
}
