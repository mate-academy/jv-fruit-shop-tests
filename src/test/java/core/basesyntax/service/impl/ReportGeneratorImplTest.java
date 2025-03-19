package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String HEADER = "fruit,quantity";
    private static final String FOOTER = ",";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int COUNT_BANANA = 152;
    private static final int COUNT_APPLE = 90;

    @BeforeAll
    static void beforeAll() {
        Storage.fruits.clear();
        reportGenerator = new ReportGeneratorImpl();
        String banana = BANANA;
        String apple = APPLE;
        Storage.fruits.put(banana, COUNT_BANANA);
        Storage.fruits.put(apple, COUNT_APPLE);
    }

    @Test
    void exceptGoodMeaning_Ok() {
        String report = reportGenerator.getReport();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER)
                .append(System.lineSeparator())
                .append(BANANA)
                .append(FOOTER)
                .append(COUNT_BANANA)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(FOOTER)
                .append(COUNT_APPLE);

        String expected = stringBuilder.toString();
        assertEquals(expected, report);
    }
}
