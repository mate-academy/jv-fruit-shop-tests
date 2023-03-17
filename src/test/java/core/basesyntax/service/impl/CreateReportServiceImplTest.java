package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStore;
import core.basesyntax.service.CreateReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static CreateReportService createReportService;

    @BeforeAll
    static void beforeAll() {
        createReportService = new CreateReportServiceImpl();
    }

    @Test
    void generateReportFromOneFruitValidDataOk() {
        FruitStore.supplies.put("banana", 19);
        String expected = TITLE + System.lineSeparator() + "banana,19";
        assertEquals(expected, createReportService.generateReport());
    }

    @Test
    void generateReportFromThreeFruitsValidDataOk() {
        FruitStore.supplies.put("banana", 19);
        FruitStore.supplies.put("apple", 20);
        FruitStore.supplies.put("orange", 11);
        String expected = TITLE + System.lineSeparator() + "banana,19"
                + System.lineSeparator() + "orange,11"
                + System.lineSeparator() + "apple,20";
        assertEquals(expected, createReportService.generateReport());
    }

    @Test
    void generateReportFromEmptyMapOk() {
        String expected = TITLE + System.lineSeparator();
        assertEquals(expected, createReportService.generateReport());
    }

    @AfterEach
    void tearDown() {
        FruitStore.supplies.clear();
    }
}
