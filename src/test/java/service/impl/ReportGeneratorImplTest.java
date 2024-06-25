package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.reports.clear();
    }

    @Test
    void getReport_consistFirstLine_Ok() {
        String expectedLine = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedLine, reportGenerator.getReport());
    }
}
