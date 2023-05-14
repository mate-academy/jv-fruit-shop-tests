package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImpTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void reportCreate_Ok() {
        FruitStorage.put("banana", 70);
        FruitStorage.put("apple", 30);
        String expectedResult = "fruit,amount" + System.lineSeparator() + "banana,70"
                + System.lineSeparator() + "apple,30";
        String actualResult = reportCreator.createReport();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void reportCreate_EmptyData_Ok() {
        String actualResult = reportCreator.createReport();
        String expecteResult = "fruit,amount";
        assertEquals(expecteResult, actualResult);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
