package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitReportServiceImplTest {

    private static final String FRUIT_APPLE = "Apple";
    private static final String FRUIT_BANANA = "Banana";
    private static final String FRUIT_ORANGE = "Orange";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 100;
    private static final int ORANGE_QUANTITY = 30;
    private static final String EXPECTED_HEADER = "fruit,quantity\n";
    private static final String COMMA = ",";
    private static final String NEWLINE = "\n";

    private static FruitReportServiceImpl reportService;

    @BeforeAll
    static void beforeAll() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put(FRUIT_APPLE, APPLE_QUANTITY);
        fruitStorage.put(FRUIT_BANANA, BANANA_QUANTITY);
        reportService = new FruitReportServiceImpl(fruitStorage);
    }

    @Test
    void getReport_validFruitStorage_ok() {
        String expectedReport = EXPECTED_HEADER
                + FRUIT_APPLE + COMMA + APPLE_QUANTITY + NEWLINE
                + FRUIT_BANANA + COMMA + BANANA_QUANTITY + NEWLINE;
        assertEquals(expectedReport, reportService.getReport());
    }

    @Test
    void getReport_emptyFruitStorage_ok() {
        Map<String, Integer> emptyFruitStorage = new HashMap<>();
        FruitReportServiceImpl emptyReportService = new FruitReportServiceImpl(emptyFruitStorage);

        String expectedReport = EXPECTED_HEADER;
        assertEquals(expectedReport, emptyReportService.getReport());
    }

    @Test
    void getReport_singleFruit_ok() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put(FRUIT_ORANGE, ORANGE_QUANTITY);
        FruitReportServiceImpl reportServiceSingle = new FruitReportServiceImpl(fruitStorage);

        String expectedReport = EXPECTED_HEADER
                + FRUIT_ORANGE + COMMA + ORANGE_QUANTITY + NEWLINE;
        assertEquals(expectedReport, reportServiceSingle.getReport());
    }
}
