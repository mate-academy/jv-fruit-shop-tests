package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl();
    }

    @Test
    void getReport() {
        String report = generator.getReport();
        assertNotNull(report);
        assertFalse(report.isEmpty());
        assertTrue(report.contains("fruit,quantity"));
    }

    @Test
    void getReport_ShouldContainCorrectHeader() {
        FruitStorage.getStorage().put("banana", 152);
        FruitStorage.getStorage().put("apple", 90);
        String report = generator.getReport();
        assertEquals("fruit,quantity\r\nbanana,152\r\napple,90\r\n", report);
    }

    @Test
    void getReport_ShouldHaveCorrectFormat() {
        String report = generator.getReport();
        String[] lines = report.split(System.lineSeparator());
        for (String line : lines) {
            assertEquals(1, line.chars().filter(ch -> ch == ',').count());
            assertFalse(line.startsWith(","));
            assertFalse(line.endsWith(","));
        }
    }

    @Test
    void getReport_ShouldContainStorageEntries() {
        FruitStorage.getStorage().put("apple", 5);
        FruitStorage.getStorage().put("banana", 3);

        String report = generator.getReport();
        String[] lines = report.split(System.lineSeparator());

        assertTrue(report.contains("apple,5"));
        assertTrue(report.contains("banana,3"));
        assertEquals(3, lines.length);
    }

    @Test
    void getReport_StorageEntriesShouldBeCorrectlyFormatted() {
        FruitStorage.getStorage().put("orange", 10);

        String report = generator.getReport();
        String[] lines = report.split("\r\n");

        assertEquals("fruit,quantity", lines[0]);
        assertEquals("orange,10", lines[1]);
    }

    @Test
    void getReport_ShouldIncludeStorageValues() {
        FruitStorage.getStorage().put("grape", 7);

        String report = generator.getReport();

        assertTrue(report.contains("7"));
        assertTrue(report.contains("grape"));
    }

    @AfterEach
    void setStorage() {
        FruitStorage.getStorage().clear();
    }
}
