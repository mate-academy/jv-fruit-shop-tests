package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_AMOUNT_LINE = "apple,50";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 50;
    private static final String VALID_REPORT = HEADER + System.lineSeparator() + FRUIT_AMOUNT_LINE;
    private static final ReportMakerService reportMaker = new ReportMakerServiceImpl();

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void generateReport_validInput_Ok() {
        String expected = VALID_REPORT;
        FruitStorage.fruitStorage.put(FRUIT, QUANTITY);
        String actual = reportMaker.generateReport(FruitStorage.fruitStorage);
        assertEquals(expected, actual);
    }
}
