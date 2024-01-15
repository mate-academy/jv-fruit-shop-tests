package core.basesyntax.services.fileprocessing;

import static core.basesyntax.services.Constants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.services.fileprocessing.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Since it is not possible to be sure of the order of the elements in the Storage hashmap,
 * it seems better not to compare two StringBuilders but to make sure that the result Builder
 * contains expected lines in it.
 */
public class ReportGeneratorImplTest {
    private static final String EXPECTED_INITIAL_LINE = "fruit,quantity";
    private static final String EXPECTED_APPLE_LINE = "apple,10";
    private static final String EXPECTED_BANANA_LINE = "banana,20";
    private static final String EXPECTED_ORANGE_LINE = "orange,30";
    private static final int APPLE_QUANTITY = 10;
    private static final int BANANA_QUANTITY = 20;
    private static final int ORANGE_QUANTITY = 30;
    private static ReportGenerator reportGeneratorImpl;

    @BeforeAll
    static void initReportGeneratorImpl() {
        reportGeneratorImpl = new ReportGeneratorImpl();
        Storage.updateFruit(APPLE, APPLE_QUANTITY);
        Storage.updateFruit(ORANGE, ORANGE_QUANTITY);
        Storage.updateFruit(BANANA, BANANA_QUANTITY);
    }

    @AfterAll
    static void closeReportGeneratorImpl() {
        reportGeneratorImpl = null;
    }

    @Test
    void generateReport_getReportWithGoodData_Ok() {
        StringBuilder builder = reportGeneratorImpl.generateReport(Storage.getFruitsEntrySet());
        assertTrue(builder.toString().contains(EXPECTED_INITIAL_LINE));
        assertTrue(builder.toString().contains(EXPECTED_APPLE_LINE));
        assertTrue(builder.toString().contains(EXPECTED_BANANA_LINE));
        assertTrue(builder.toString().contains(EXPECTED_ORANGE_LINE));
    }
}
