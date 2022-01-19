package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.Report;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final Report report = new ReportImpl();

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.put(new Fruit("banana"), 60);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void createReport_Ok() {
        List<String> expected = new ArrayList<>(List.of(TITLE, "banana,60", "apple,100"));
        List<String> actual = report.createReport();
        assertEquals(expected, actual);
    }

    @Test (expected = AssertionError.class)
    public void createReport_NotOk() {
        List<String> expected = new ArrayList<>(List.of(TITLE, "banana,60", "apple,100"));
        List<String> actual = report.createReport();
        assertNotEquals(expected, actual);
    }
}
