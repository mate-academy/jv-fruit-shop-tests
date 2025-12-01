package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl();
    }

    @Test
    void build_validMap_ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("banana", 152);
        fruits.put("apple", 90);
        String result = generator.build(fruits);
        assertTrue(result.contains("fruit,quantity"));
        assertTrue(result.contains("apple,90"));
        assertTrue(result.contains("banana,152"));
    }

    @Test
    void build_sortedAlphabetically_ok() {
        Map<String, Integer> fruits = new LinkedHashMap<>();
        fruits.put("orange", 50);
        fruits.put("apple", 100);
        fruits.put("banana", 75);
        String result = generator.build(fruits);
        int appleIndex = result.indexOf("apple");
        int bananaIndex = result.indexOf("banana");
        int orangeIndex = result.indexOf("orange");
        assertTrue(appleIndex < bananaIndex);
        assertTrue(bananaIndex < orangeIndex);
    }

    @Test
    void build_emptyMap_returnsOnlyHeader() {
        Map<String, Integer> fruits = new HashMap<>();
        String result = generator.build(fruits);
        assertEquals("fruit,quantity", result);
    }

    @Test
    void build_nullMap_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> generator.build(null));
    }

    @Test
    void build_singleEntry_ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 100);
        String result = generator.build(fruits);
        String[] lines = result.split(System.lineSeparator());
        assertEquals(2, lines.length);
        assertEquals("fruit,quantity", lines[0]);
        assertEquals("apple,100", lines[1]);
    }

    @Test
    void build_zeroQuantity_ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 0);
        String result = generator.build(fruits);
        assertTrue(result.contains("apple,0"));
    }

    @Test
    void build_multipleEntries_correctFormat() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 100);
        fruits.put("banana", 200);
        fruits.put("cherry", 50);
        String result = generator.build(fruits);
        String[] lines = result.split(System.lineSeparator());
        assertEquals(4, lines.length);
        assertEquals("fruit,quantity", lines[0]);
    }

    @Test
    void build_specialCharactersInFruitName_ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple-red", 100);
        String result = generator.build(fruits);
        assertTrue(result.contains("apple-red,100"));
    }
}
