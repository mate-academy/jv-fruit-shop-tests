package service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ReportMakerImpl;

public class ReportMakerTest {
    private static String EXPECTED;
    private static Map<Fruit, Integer> storage;
    private static ReportMaker reportMaker;

    @BeforeClass
    public static void beforeClass() {
        EXPECTED = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        storage = new HashMap<>();
        reportMaker = new ReportMakerImpl();
        storage.put(new Fruit("banana"), 152);
        storage.put(new Fruit("apple"), 90);
    }

    @Test
    public void reportIsValid_Ok() {
        String actual = reportMaker.createReport(storage.entrySet());
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void emptyReport_NotOk() {
        Map<Fruit, Integer> emptyMap = new HashMap<>();
        String expected = "fruit,quantity";
        String actual = reportMaker.createReport(emptyMap.entrySet());
        assertEquals(expected, actual);
    }
}
