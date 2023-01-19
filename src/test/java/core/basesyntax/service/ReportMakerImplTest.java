package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportMakerImpl;
import java.util.ArrayList;
import java.util.List;
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
    public void makeReport_normal_Ok() {
        Storage.fruitStorage.put("test1", 2);
        Storage.fruitStorage.put("test2", 8);
        Storage.fruitStorage.put("test3", 3);

        List<String> expected1 = new ArrayList<>();
        expected1.add("fruit,quantity");
        expected1.add("test1,2");
        expected1.add("test2,8");
        expected1.add("test3,3");

        List<String> expected2 = new ArrayList<>();
        expected2.add("fruit,quantity");
        expected2.add("test2,8");
        expected2.add("test3,3");
        expected2.add("test1,2");
        List<String> actual = reportMaker.makeReport();
        assertTrue(expected1.equals(actual) || expected2.equals(actual));
    }

    @Test
    public void makeReport_empty_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        List<String> actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
