package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.Report;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportImplTest {
    private static final String TITLE = "fruit,quantity";
    private static List<String> expected = new ArrayList<>();
    private static final Report REPORT = new ReportImpl();

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.put(new Fruit("banana"), 60);
        Storage.storage.put(new Fruit("apple"), 100);
        expected = List.of(TITLE, "banana,60", "apple,100");
    }

    @Test
    public void createReport_Ok() {
        List<String> actual = REPORT.createReport();
        assertEquals(expected, actual);
    }

    @Test (expected = AssertionError.class)
    public void createReport_NotOk() {
        Storage.storage.put(new Fruit("orange"), 100);
        List<String> actual = REPORT.createReport();
        assertEquals(expected, actual);
    }
}
