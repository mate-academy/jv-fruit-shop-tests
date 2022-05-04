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
    private static Report report;

    @BeforeClass
    public static void setUp() {
        report = new ReportImpl();
        Storage.storage.put(new Fruit("banana"), 56);
        Storage.storage.put(new Fruit("apple"), 25);
    }

    @Test
    public void createNewReport_ok() {
        List<String> expected = new ArrayList<>(List.of("fruit,quantity", "banana,56", "apple,25"));
        List<String> actual = report.createNewReport();
        assertEquals(expected,actual);
    }
}
