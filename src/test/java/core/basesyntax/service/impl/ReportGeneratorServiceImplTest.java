package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private static final String REPORT_FIRST_LINE = "fruit,quantity";
    private static final String COMMA_SEPARATOR = ",";
    private static ReportGeneratorServiceImpl reportGeneratorService;

    @BeforeAll
    static void beforeAll() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storageMap.clear();
    }

    @DisplayName("Checking for generating report if storage is empty")
    @Test
    void generate_emptyStorage_ok() {
        String actual = reportGeneratorService.generate();
        assertEquals(REPORT_FIRST_LINE, actual);
    }

    @DisplayName("Checking for generating report when storage contains one element")
    @Test
    void generate_oneElementInStorage_ok() {
        String fruit = "banana";
        int quantity = 10;
        Storage.storageMap.put(fruit, quantity);
        String expected = REPORT_FIRST_LINE + System.lineSeparator()
                + fruit + "," + quantity;
        String actual = reportGeneratorService.generate();
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for generating report when storage contains multiple elements")
    @Test
    void generate_multipleElementsInStorage_ok() {
        String banana = "banana";
        int bananaQuantity = 10;
        String apple = "apple";
        int appleQuantity = 20;
        String pineapple = "pineapple";
        int pineappleQuantity = 30;
        Storage.storageMap.put(banana, bananaQuantity);
        Storage.storageMap.put(apple, appleQuantity);
        Storage.storageMap.put(pineapple, pineappleQuantity);
        String actual = reportGeneratorService.generate();
        String expected = REPORT_FIRST_LINE + System.lineSeparator()
                + banana + COMMA_SEPARATOR + bananaQuantity + System.lineSeparator()
                + apple + COMMA_SEPARATOR + appleQuantity + System.lineSeparator()
                + pineapple + COMMA_SEPARATOR + pineappleQuantity;
        assertEquals(expected, actual);
    }
}
