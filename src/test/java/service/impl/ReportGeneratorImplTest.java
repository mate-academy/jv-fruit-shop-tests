package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_consistFirstLine_Ok() {
        Storage.reports.clear();
        String expectedLine = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedLine, reportGenerator.getReport());
    }
}
