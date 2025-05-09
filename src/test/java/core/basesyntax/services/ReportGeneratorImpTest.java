package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReportGeneratorImpTest {
    private ReportGenerator reportGenerator;
    private StorageService storageServiceMock;

    @BeforeEach
    void setUp() {
        storageServiceMock = Mockito.mock(StorageService.class);
        reportGenerator = new ReportGeneratorImp(storageServiceMock);
    }

    @Test
    void generateReport_IsWorking_Fine() {
        Map<String, Integer> mockMap = new LinkedHashMap<>();
        mockMap.put("apple", 10);
        mockMap.put("banana", 5);

        when(storageServiceMock.getAll()).thenReturn(mockMap);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,5" + System.lineSeparator();
        String actual = reportGenerator.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void generateReport_WithNoElements() {
        Map<String, Integer> mockMap = new LinkedHashMap<>();
        when(storageServiceMock.getAll()).thenReturn(mockMap);
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_WithNull() {
        Map<String, Integer> mockMap = new LinkedHashMap<>();
        mockMap.put(null, null);
        when(storageServiceMock.getAll()).thenReturn(mockMap);
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
