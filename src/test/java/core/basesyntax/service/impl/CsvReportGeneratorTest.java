package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReportGeneratorTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String MANGO = "mango";
    private static final int BANANA_QUANTITY = 100;
    private static final int APPLE_QUANTITY = 90;
    private static final int MANGO_QUANTITY = 140;
    private static final String EXPECTED = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "mango,140";
    private static ReportGenerator csvReportGenerator;

    @BeforeAll
    static void beforeAll() {
        csvReportGenerator = new CsvReportGenerator();
    }

    @Test
    void createReport_isOk() {
        Storage.fruits.put(BANANA, BANANA_QUANTITY);
        Storage.fruits.put(APPLE, APPLE_QUANTITY);
        Storage.fruits.put(MANGO, MANGO_QUANTITY);
        String actualReport = csvReportGenerator.generateReport();
        assertEquals(EXPECTED, actualReport);
    }
}
