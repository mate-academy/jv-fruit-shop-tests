package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Producer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProduceMapReportServiceTest {
    private static final String VALID_DATA = "banana,152" + System.lineSeparator() + "apple,90";
    private static final String EMPTY_DATA = "";
    private static final String ZERO_QUANTITY_DATA = "banana,0";
    private static final String SINGLE_FRUIT_DATA = "banana,5";
    private static final String LARGE_QUANTITY_DATA = "banana,1000"
            + System.lineSeparator()
            + "apple,2000";
    private static Producer produceMapReportService;
    private static Map<String, Integer> map;

    @BeforeAll
    static void setUp() {
        produceMapReportService = new ProduceMapReportService();
        map = new LinkedHashMap<>();
    }

    @AfterEach
    public void afterEach() {
        map.clear();
    }

    @Test
    void validData_Ok() {
        map.put("banana", 152);
        map.put("apple", 90);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(VALID_DATA, actual);
    }

    @Test
    void nullData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> produceMapReportService.produceReport(null));
    }

    @Test
    void emptyData_NotOK() {
        String actual = produceMapReportService.produceReport(Map.of());
        assertEquals(EMPTY_DATA, actual);
    }

    @Test
    void negativeQuantity_NotOk() {
        map.put("banana", -5);
        assertThrows(IllegalArgumentException.class,
                () -> produceMapReportService.produceReport(map));
    }

    @Test
    void zeroQuantity_Ok() {
        map.put("banana", 0);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(ZERO_QUANTITY_DATA, actual);
    }

    @Test
    void singleFruit_Ok() {
        map.put("banana", 5);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(SINGLE_FRUIT_DATA, actual);
    }

    @Test
    void largeQuantity_Ok() {
        map.put("banana", 1000);
        map.put("apple", 2000);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(LARGE_QUANTITY_DATA, actual);
    }
}
