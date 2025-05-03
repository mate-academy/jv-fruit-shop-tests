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
        String[] fruits = {"apple", "banana", "orange"};
        Set<String> expectedSet = new HashSet<>();
        for (int i = 0; i < fruits.length; i++) {
            expectedSet.add(fruits[i] + ", " + i);
        }
        Map<String, Integer> storage = new HashMap<>();
        for (int i = 0; i < fruits.length; i++) {
            storage.put(fruits[i], i);
        }
        //I did what you asked here, but I think, readability got worse.
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
