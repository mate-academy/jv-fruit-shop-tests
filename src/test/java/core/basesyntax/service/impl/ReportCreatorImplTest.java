package core.basesyntax.service.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String EXPECTED = "fruit,quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "melon,30" + System.lineSeparator()
            + "apple,70";
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportCreator = new ReportCreatorImpl();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruits.add(new Fruit("banana", 50));
        Storage.fruits.add(new Fruit("melon", 30));
        Storage.fruits.add(new Fruit("apple", 70));
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_ok() {
        String actual = reportCreator.createReport();
        Assert.assertEquals(EXPECTED, actual);
    }
}
