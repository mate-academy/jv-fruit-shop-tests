package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUp() {
        reportCreator = new ReportCreatorImpl(new FruitsDaoImpl());
    }

    @Test
    public void createEmptyReport_ok() {
        assertEquals("Empty storage should have empty report!",
                "", reportCreator.createReport());
    }

    @Test
    public void createReport_twoFruits_ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String actual = reportCreator.createReport();
        String expected = System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
        assertEquals("Report creator don't work correctly!", expected, actual);
    }

    @Test
    public void createReport_threeFruits_ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        Storage.fruits.put("strawberry", 74);
        String actual = reportCreator.createReport();
        String expected = System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90"
                + System.lineSeparator() + "strawberry,74";
        assertEquals("Report creator don't work correctly!", expected, actual);
    }

    @After
    public void afterEachMethod() {
        Storage.fruits.clear();
    }
}
