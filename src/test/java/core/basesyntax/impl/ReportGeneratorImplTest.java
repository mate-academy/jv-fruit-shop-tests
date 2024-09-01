package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 20;
    private static final int BANANA_QUANTITY = 10;
    private static final String HEADER = "fruit,quantity";
    private static final String BANANA_PART = "banana,10";
    private static final String APPLE_PART = "apple,20";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_correct_ok() {
        Storage.addFruit(BANANA, BANANA_QUANTITY);
        Storage.addFruit(APPLE, APPLE_QUANTITY);
        String report = reportGenerator.getReport();
        String expectedReport = HEADER + System.lineSeparator() +
                BANANA_PART + System.lineSeparator() +
                APPLE_PART + System.lineSeparator();
        assertEquals(expectedReport, report);
    }

}
