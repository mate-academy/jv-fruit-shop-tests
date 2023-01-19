package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

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

        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("test1,2");
        expected.add("test2,8");
        expected.add("test3,3");
        List<String> actual = reportMaker.makeReport();
        assertEquals(expected, actual);
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
