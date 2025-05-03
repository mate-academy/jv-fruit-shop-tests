package core.basesyntax.data.impl;

import core.basesyntax.data.db.Storage;
import core.basesyntax.data.servise.ReportGenerator;
import core.basesyntax.data.servise.ipl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMMA = ",";
    private static final String HEADER = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 100;
    private static final int BANANA_QUANTITY = 50;
    private static final String EXPECTED = HEADER + LINE_SEPARATOR
            + BANANA + COMMA + BANANA_QUANTITY + LINE_SEPARATOR + APPLE + COMMA
            + APPLE_QUANTITY + LINE_SEPARATOR;
    private static final String EXPECTED_EMPTY_STORAGE = HEADER + LINE_SEPARATOR;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void getReport_fromValidData_Ok() {
        Storage.fruitsStorage.put(APPLE, APPLE_QUANTITY);
        Storage.fruitsStorage.put(BANANA, BANANA_QUANTITY);
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(EXPECTED, actual);
    }

    @Test
    void getReport_fromEmptyStorage_Ok() {
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(EXPECTED_EMPTY_STORAGE, actual);
    }
}
