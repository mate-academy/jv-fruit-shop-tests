package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReportGeneratorImplTest {

    @Mock
    private ShopService shopService;

    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportGenerator = new ReportGeneratorImpl(shopService);
    }

    @Test
    void getReport_validData_ok() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 50);
        storage.put("banana", 100);

        when(shopService.getStorage()).thenReturn(storage);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_emptyStorage_ok() {
        Map<String, Integer> emptyStorage = new HashMap<>();
        when(shopService.getStorage()).thenReturn(emptyStorage);

        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport);
    }
}
