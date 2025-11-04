package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import core.basesyntax.dao.StorageDao;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReportGeneratorImplTest {
    private StorageDao storageDao;
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        storageDao = mock(StorageDao.class);
        reportGenerator = new ReportGeneratorImpl(storageDao);
    }

    @Test
    void constructor_nullStorageDao_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> new ReportGeneratorImpl(null));
        assertEquals("Cannot access to storage dao: parameter is null",
                exception.getMessage());
    }

    @Test
    void getReport_emptyStorage_Ok() {
        Mockito.when(storageDao.getAllFruitsQuantities()).thenReturn(new HashMap<>());
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_filledStorage_ok() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 20);
        Mockito.when(storageDao.getAllFruitsQuantities()).thenReturn(storage);

        String actual = reportGenerator.getReport();
        String header = "fruit,quantity" + System.lineSeparator();
        String option1 = header + "apple,10" + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        String option2 = header + "banana,20" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();

        boolean matches = actual.equals(option1) || actual.equals(option2);
        assertTrue(matches, "Report should contain all fruits in any order");
    }
}
