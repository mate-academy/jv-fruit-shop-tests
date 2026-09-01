package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validStorage_returnsCsvReport() {
        Storage.getStorage().put("banana", 152);
        Storage.getStorage().put("apple", 90);

        String actual = reportGenerator.getReport();

        assertAll(
                () -> assertTrue(actual.startsWith("fruit,quantity")),
                () -> assertTrue(actual.contains("banana,152")),
                () -> assertTrue(actual.contains("apple,90"))
        );

        String[] lines = actual.split(System.lineSeparator());

        assertEquals(3, lines.length);
    }

    @Test
    void getReport_emptyStorage_returnsHeaderOnly() {
        String actual = reportGenerator.getReport();

        assertEquals("fruit,quantity", actual);
    }
}
