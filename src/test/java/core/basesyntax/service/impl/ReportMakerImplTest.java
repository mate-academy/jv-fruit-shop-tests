package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMaker;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ReportMaker reportMaker;

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new ReportMakerImpl();
    }

    @Test
    public void makeReport_ok() {
        Storage.fruitStore.add(new Fruit("banana", 20));
        Storage.fruitStore.add(new Fruit("apple", 30));
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,30" + System.lineSeparator();
        String actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReportFromEmptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReportWithOneFruit_ok() {
        Storage.fruitStore.add(new Fruit("banana", 20));
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        String actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStore.clear();
    }
}
