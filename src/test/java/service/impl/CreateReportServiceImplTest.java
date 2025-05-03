package service.impl;

import static org.junit.Assert.assertEquals;

import db.FruitStore;
import org.junit.After;
import org.junit.Test;
import service.CreateReportService;

public class CreateReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static CreateReportService createReportService = new CreateReportServiceImpl();

    @Test
    public void generateReportFromOneFruitValidDataOk() {
        FruitStore.supplies.put("banana", 19);
        String expected = TITLE + System.lineSeparator() + "banana,19";
        assertEquals(expected, createReportService.generateReport());
    }

    @Test
    public void generateReportFromThreeFruitsValidDataOk() {
        FruitStore.supplies.put("banana", 19);
        FruitStore.supplies.put("apple", 20);
        FruitStore.supplies.put("orange", 11);
        String expected = TITLE + System.lineSeparator() + "banana,19"
                + System.lineSeparator() + "orange,11"
                + System.lineSeparator() + "apple,20";
        assertEquals(expected, createReportService.generateReport());
    }

    @Test
    public void generateReportFromEmptyMapOk() {
        String expected = TITLE + System.lineSeparator();
        assertEquals(expected, createReportService.generateReport());
    }

    @After
    public void tearDown() {
        FruitStore.supplies.clear();
    }
}
