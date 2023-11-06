package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitSalesReportGeneratorTest {

    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90" + System.lineSeparator();
    private static ReportGenerator fruitSalesReportGenerator;

    @BeforeAll
    static void beforeAll() {
        fruitSalesReportGenerator = new FruitSalesReportGenerator();
        Storage.getFruitBalance().put(BANANA, BANANA_QUANTITY);
        Storage.getFruitBalance().put(APPLE, APPLE_QUANTITY);
    }

    @Test
    void generate_Report_Valid_Data_Ok() {
        String actual = fruitSalesReportGenerator.generateReport();
        assertEquals(actual, report);
    }
}
