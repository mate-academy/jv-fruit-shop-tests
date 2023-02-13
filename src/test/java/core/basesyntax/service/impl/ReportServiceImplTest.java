package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String SEPARATOR = ",";
    private static final String EXPECTED_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,10" + System.lineSeparator() + "apple,15";
    private ReportService reportService = new ReportServiceImpl();

    @Before
    public void setUp() {
        FruitStorage.fruits.put("banana", 10);
        FruitStorage.fruits.put("apple", 15);
    }

    @Test
    public void reportService_CorrectFormation_Ok() {
        String actual = reportService.report();
        assertEquals(EXPECTED_DATA, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
