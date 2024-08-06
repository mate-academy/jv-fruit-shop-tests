package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void getReport_getReportFromCorrectData_ok() {
        Storage.addFruit(APPLE, 50);
        Storage.addFruit(BANANA, 15);
        String expected = HEADER + "apple,50" + System.lineSeparator() + "banana,15";
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_getReportFromEmptyStorage_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportGenerator.getReport());
    }
}
