package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImpTest {
    private static final int ZERO = 0;
    private ReportGenerator reportGenerator;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        reportGenerator = new ReportGeneratorImp(storageService);
    }

    @AfterEach
    void tearDown() {
        storageService.clear();
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
    void generateReport_ShouldNotAddNullFruit() {
        assertThrows(IllegalArgumentException.class, () -> {
            storageService.add(null, ZERO);
        });
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
