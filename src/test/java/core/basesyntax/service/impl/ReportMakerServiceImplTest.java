package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportMakerServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_AMOUNT_LINE = "apple,50";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 50;
    private static final String VALID_REPORT = HEADER + System.lineSeparator() + FRUIT_AMOUNT_LINE;
    private static ReportMakerService reportMaker;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new ReportMakerServiceImpl();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void generateReport_validInput_ok() {
        String expected = VALID_REPORT;
        FruitStorage.fruitStorage.put(FRUIT, QUANTITY);
        String actual = reportMaker.generateReport(FruitStorage.fruitStorage);
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyStorage_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Storage is empty, can't generate an report");
        reportMaker.generateReport(FruitStorage.fruitStorage);
    }
}
