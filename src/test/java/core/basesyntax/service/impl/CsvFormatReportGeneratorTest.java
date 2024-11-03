package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitStorageDao;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CsvFormatReportGeneratorTest {
    @Mock
    private FruitStorageDao storageDao;

    private CsvFormatReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reportGenerator = new CsvFormatReportGenerator(storageDao);
    }

    @Test
    public void testCreate_withData_ok() {
        Set<Map.Entry<String, Integer>> storageData = Set.of(
                Map.entry("apple", 50),
                Map.entry("banana", 30)
        );
        when(storageDao.getAll()).thenReturn(storageData);
        when(storageDao.getQuantity("apple")).thenReturn(50);
        when(storageDao.getQuantity("banana")).thenReturn(30);

        String report = reportGenerator.create();

        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,30";
        assertEquals(expectedReport, report, "Report should match expected CSV format");
    }

    @Test
    public void testCreate_withEmptyStorage_ok() {
        when(storageDao.getAll()).thenReturn(Set.of());

        String report = reportGenerator.create();

        String expectedReport = "fruit,quantity";
        assertEquals(expectedReport, report,
                "Report should only contain the header when storage is empty");
    }
}
