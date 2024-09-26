package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String SEPARATOR = System.lineSeparator();
    private ReportGeneratorImpl reportGenerator;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        shopService = new ShopServiceImpl();
    }

    @Test
    void emptyStorage_OK() {
        String expected = "";
        assertEquals(expected, reportGenerator.getReport(shopService));
    }

    @Test
    void oneFruit_OK() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        shopService.setStorage(storage);
        String expected = "apple, 10";
        assertEquals(expected, reportGenerator.getReport(shopService));
    }

    @Test
    void multipleFruits_OK() {
        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("apple, 10");
        expectedSet.add("banana, 20");
        expectedSet.add("orange, 30");
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 20);
        storage.put("orange", 30);
        shopService.setStorage(storage);
        String actual = reportGenerator.getReport(shopService);
        Set<String> actualSet = new HashSet<>(Arrays.asList(actual.split(SEPARATOR)));
        assertEquals(expectedSet, actualSet);
    }

    @Test
    void nullQuantity_NotOK() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", null);
        shopService.setStorage(storage);
        assertThrows(IllegalArgumentException.class, () -> reportGenerator.getReport(shopService));
    }
}
