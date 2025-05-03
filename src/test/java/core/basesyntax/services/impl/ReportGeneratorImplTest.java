package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.StringJoiner;
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
    private static final int ZERO_QUANTITY = 0;
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
    void reportInfo_validData_ok() {
        Storage.fruits.put(APPLE, 10);
        Storage.fruits.put(BANANA, 10);
        String actual = reportGenerator.getReport();
        String expected = new StringJoiner(System.lineSeparator(),"",System.lineSeparator())
                .add(TITLE).add(BANANAS).add(APPLES).toString();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_bananaWithZeroQuantity_ok() {
        Storage.fruits.put(BANANA, ZERO_QUANTITY);
        String expected = new StringJoiner(System.lineSeparator(),"",System.lineSeparator())
                .add(TITLE).add(BANANA_WITH_ZERO_VALUE).toString();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_applesWithNegativeQuantity_ok() {
        Storage.fruits.put(APPLE, NEGATIVE_VALUE);
        String expected = new StringJoiner(System.lineSeparator(),"",System.lineSeparator())
                .add(TITLE).add(APPLE_WITH_NEGATIVE_VALUE).toString();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void reportInfo_fromEmptyStorage_ok() {
        String expected = new StringBuilder().append(TITLE)
                .append(System.lineSeparator()).toString();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
