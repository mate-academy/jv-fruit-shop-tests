package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportTextGeneratorService;
import core.basesyntax.serviceimpl.ReportTextGeneratorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReportTextGeneratorTest {
    private static ReportTextGeneratorService reportTextGenerator;

    @BeforeAll
    static void setUp() {
        reportTextGenerator = new ReportTextGeneratorServiceImpl();
    }

    @DisplayName("Valid report generation")
    @Test
    void reportTextGenerator_ValidData_Ok() {
        Storage.STORAGE.put("Apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "Apple,100" + System.lineSeparator();
        Assertions.assertEquals(expected, reportTextGenerator.generateTextReport());
    }

    @DisplayName("Empty Storage test")
    @Test
    void reportTextGenerator_EmptyStorage_Ok() {
        Assertions.assertEquals("fruit,quantity" + System.lineSeparator(),
                reportTextGenerator.generateTextReport());
    }
}
