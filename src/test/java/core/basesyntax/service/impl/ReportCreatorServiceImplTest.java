package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorServiceImpl reportCreator;

    @Before
    public void setUp() throws Exception {
        reportCreator = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_validData_Ok() {
        FruitStorage.put("banana", 20);
        FruitStorage.put("apple", 100);
        String expectedReport = "fruit,quantity"
                + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,100";
        String actualReport = reportCreator.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyData_Ok() {
        String actualReport = reportCreator.createReport();
        String expectedReport = "fruit,quantity";
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
