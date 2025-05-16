package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.storage.put("banana", 40);
    }

    @Test
    void getReport_buildsCorrectReportString_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                            + "banana,40" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
