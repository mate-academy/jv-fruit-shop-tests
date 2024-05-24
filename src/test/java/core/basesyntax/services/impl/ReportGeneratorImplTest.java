package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String APPLES = "apple,10";
    private static final String APPLE_WITH_NEGATIVE_VALUE = "apple,-1";
    private static final String BANANAS = "banana,10";
    private static final String BANANA_WITH_ZERO_VALUE = "banana,0";
    private static final int ZERO = 0;
    private static final int NEGATIVE_VALUE = -1;
    private static ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void reportInfo_ValidData_ok() {
        Storage.fruits.put(APPLE, 10);
        Storage.fruits.put(BANANA, 10);
        String actual = reportGenerator.getReport();
        String expected = TITLE + System.lineSeparator()
                + BANANAS + System.lineSeparator()
                + APPLES + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_bananaWithZeroQuantity_ok() {
        Storage.fruits.put(BANANA, ZERO);
        String expected = TITLE + System.lineSeparator()
                + BANANA_WITH_ZERO_VALUE + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_applesWithNegativeQuantity_ok() {
        Storage.fruits.put(APPLE, NEGATIVE_VALUE);
        String expected = TITLE + System.lineSeparator()
                + APPLE_WITH_NEGATIVE_VALUE + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_fromEmptyStorage_ok() {
        String expected = TITLE + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
