package core.basesyntax.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void generateReportFromEmptyStorage_ShouldThrowException() {
        Storage.getStorage().clear();
        assertThrows(RuntimeException.class, () -> reportGenerator.generateReport());
    }

    @Test
    void generateReportFromValidStorage_ShouldReturnString() {
        Storage.getStorage().put("banana", 20);
        Storage.getStorage().put("apple", 12);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,12" + System.lineSeparator();
        String actual = reportGenerator.generateReport();

        assertEquals(expectedResult, actual);
        assertNotNull(actual);
    }
}
