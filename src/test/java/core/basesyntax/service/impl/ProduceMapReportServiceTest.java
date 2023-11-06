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
    private String expected;

    @BeforeAll
    public static void setUp() {
        produceMapReportService = new ProduceMapReportService();
        map = new LinkedHashMap<>();
    }

    @AfterEach
    public void afterEach() {
        map.clear();
    }

    @Test
    public void produceMapReportService_ValidData_Ok() {
        expected = VALID_DATA;
        map.put("banana", 152);
        map.put("apple", 90);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(expected, actual);
    }

    @Test
    public void produceMapReportService_NullData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> produceMapReportService.produceReport(null));
    }

    @Test
    public void produceMapReportService_EmptyData_NotOK() {
        expected = EMPTY_DATA;
        String actual = produceMapReportService.produceReport(Map.of());
        assertEquals(expected, actual);
    }

    @Test
    public void produceMapReportService_NegativeQuantity_NotOk() {
        map.put("banana", -5);
        assertThrows(IllegalArgumentException.class,
                () -> produceMapReportService.produceReport(map));
    }

    @Test
    public void produceMapReportService_ZeroQuantity_Ok() {
        map.put("banana", 0);
        expected = ZERO_QUANTITY_DATA;
        String actual = produceMapReportService.produceReport(map);
        assertEquals(expected, actual);
    }

    @Test
    public void produceMapReportService_SingleFruit_Ok() {
        expected = SINGLE_FRUIT_DATA;
        map.put("banana", 5);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(expected, actual);
    }

    @Test
    public void produceMapReportService_LargeQuantity_Ok() {
        expected = LARGE_QUANTITY_DATA;
        map.put("banana", 1000);
        map.put("apple", 2000);
        String actual = produceMapReportService.produceReport(map);
        assertEquals(expected, actual);
    }
}
