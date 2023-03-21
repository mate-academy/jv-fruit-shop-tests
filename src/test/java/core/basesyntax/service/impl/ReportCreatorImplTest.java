package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @Before
    public void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_ValidCase_Ok() {
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana, 152" + System.lineSeparator()
                + "apple, 90" + System.lineSeparator();
        Storage.getFruitStorage().put("banana", 152);
        Storage.getFruitStorage().put("apple", 90);
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        String expected = "Storage is empty.";
        String actual = new ReportCreatorImpl().createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
