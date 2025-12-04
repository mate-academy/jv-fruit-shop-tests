package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Storage storage;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_header_isOk() {
        storage.put("apple",5);
        String expected = "fruit,quantity";
        String[] actual = reportGenerator.getReport().split(System.lineSeparator());
        assertEquals(expected,actual[0]);
    }

    @Test
    void getReport_emptyStorage_onlyHeader_isOk() {
        String report = reportGenerator.getReport();
        String[] lines = report.split(System.lineSeparator());

        assertEquals(1, lines.length);
        assertEquals("fruit,quantity", lines[0]);
    }

    @Test
    void getReport_oneFruit_isOk() {
        storage.put("apple", 5);

        String report = reportGenerator.getReport();
        String[] lines = report.split(System.lineSeparator());

        assertEquals("fruit,quantity", lines[0]);
        assertEquals("apple,5", lines[1]);
    }

    @Test
    void getReport_multipleFruits_containsAllLines_isOk() {
        storage.put("apple", 10);
        storage.put("banana", 5);

        String report = reportGenerator.getReport();

        assert (report.contains("apple,10"));
        assert (report.contains("banana,5"));
    }

    @AfterEach
    public void afterEachTest() {
        storage.getAll().clear();
    }
}
