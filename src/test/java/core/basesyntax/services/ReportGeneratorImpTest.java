package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImpTest {
    private ReportGenerator reportGenerator;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear();
        reportGenerator = new ReportGeneratorImp(storageService);
    }

    @Test
    void generateReport_IsWorking_Fine() {
        storageService.add("apple", 10);
        storageService.add("banana", 5);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,5" + System.lineSeparator();

        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_WithNoElements() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_WithNull() {
        Storage.fruitMap.put(null, null); // пряме використання для перевірки null
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
