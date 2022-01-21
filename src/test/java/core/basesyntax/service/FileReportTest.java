package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FileReportImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class FileReportTest {
    private static FileReport fileReport;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReport = new FileReportImpl();
    }

    @Test
    public void createReport_Ok() {
        List<String> expect = List.of("fruit,quantity", "banana,20");
        Storage.fruits.put(new Fruit("banana"), 20);
        List<String> actual = fileReport.getReport(Storage.fruits);
        assertEquals(expect, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
