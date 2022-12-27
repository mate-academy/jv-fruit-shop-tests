package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String TEST_HEADER = "fruit,quantity\n";
    private static final String TEST_FRUIT_FIRST = "apple";
    private static final String TEST_FRUIT_SECOND = "banana";
    private static final int TEST_BALANCE_FIRST = 50;
    private static final int TEST_BALANCE_SECOND = 100;
    private ReportCreator reportCreator;

    @Before
    public void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Work_Ok() {
        Storage.fruits.put(TEST_FRUIT_FIRST, TEST_BALANCE_FIRST);
        Storage.fruits.put(TEST_FRUIT_SECOND, TEST_BALANCE_SECOND);
        String actual = reportCreator.createReport();
        String expected = TEST_HEADER + "banana,100\n" + "apple,50";
        assertEquals(expected, actual);
    }
}
