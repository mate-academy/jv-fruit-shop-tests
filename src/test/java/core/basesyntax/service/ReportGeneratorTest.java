package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;
    private Storage storage;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void getReport_getReportFromCorrectData_ok() {
        storage.addFruit("apple", 50);
        storage.addFruit("banana", 15);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator() + "banana,15";
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_getReportFromEmptyStorage_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportGenerator.getReport());
    }
}
