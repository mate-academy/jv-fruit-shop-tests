package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void getReport_emptyStorage_ok() {
        assertEquals(HEADER, reportGenerator.getReport());
    }

    @Test
    void getReport_oneFruit_ok() {
        Storage.getFruits().put("banana", 20);

        String report = reportGenerator.getReport();

        assertEquals(HEADER + System.lineSeparator() + "banana,20", report);
    }

    @Test
    void getReport_multipleFruits_ok() {
        Storage.getFruits().put("banana", 20);
        Storage.getFruits().put("apple", 100);

        String report = reportGenerator.getReport();

        assertTrue(report.startsWith(HEADER));
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("apple,100"));
    }
}
