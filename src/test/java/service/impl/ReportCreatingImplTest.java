package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportCreating;

class ReportCreatingImplTest {
    public static final String HEADER = "fruit, quantity";
    private Storage storage;
    private ReportCreating reportCreating;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportCreating = new ReportCreatingImpl(storage);
    }

    @Test
    void generateReport_emptyStorage_Ok() {
        String[] lines = reportCreating.generateReport().split(System.lineSeparator());
        assertEquals(HEADER, lines[0]);
        assertEquals(1, lines.length);
    }

    @Test
    void generateReport_oneValue_Ok() {
        storage.put("apple", 11);
        String[] lines = reportCreating.generateReport().split(System.lineSeparator());
        assertEquals(HEADER, lines[0]);
        assertEquals("apple,11", lines[1]);
        assertEquals(2, lines.length);
    }

    @Test
    void generateReport_twoOrMoreValue_Ok() {
        storage.put("apple", 11);
        storage.put("banana", 43);
        String report = reportCreating.generateReport();
        assertTrue(report.contains(HEADER));
        assertTrue(report.contains("apple,11"));
        assertTrue(report.contains("banana,43"));
    }

    @AfterEach
    void tearDown() {
        storage.getAll().clear();
    }
}
