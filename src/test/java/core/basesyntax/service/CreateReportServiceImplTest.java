package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final int APPLE_QUANTITY = 990;
    private static final int BANANA_QUANTITY = 2132;
    private static final int ORANGE_QUANTITY = 470;
    private static CreateReportService createReportService;

    @BeforeClass
    public static void setUp() {
        createReportService = new CreateReportServiceImpl();

        FruitStorage.fruitsStorage.put("apple", APPLE_QUANTITY);
        FruitStorage.fruitsStorage.put("banana", BANANA_QUANTITY);
        FruitStorage.fruitsStorage.put("orange", ORANGE_QUANTITY);
    }

    @Test
    public void createReport_checkIfCorrect_isOk() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,2132" + System.lineSeparator()
                + "orange,470" + System.lineSeparator()
                + "apple,990" + System.lineSeparator();

        assertEquals(expected, createReportService.createReport());
    }
}
