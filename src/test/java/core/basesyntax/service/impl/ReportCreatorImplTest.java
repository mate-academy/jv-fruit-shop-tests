package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreatorImpl reportCreator;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 20);
        Storage.storage.put(new Fruit("apple"), 1);
    }

    @Test
    public void report_correctInput_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,1";
        String actual = reportCreator.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
