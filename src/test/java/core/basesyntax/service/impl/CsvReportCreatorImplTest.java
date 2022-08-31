package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvReportCreatorImplTest {
    private static StorageDao storageDao;
    private static FruitService fruitService;
    private static ReportCreator reportCreator;

    @Before
    public void init() {
        storageDao = new StorageDaoImpl();
        fruitService = new FruitServiceImpl(storageDao);
        reportCreator = new CsvReportCreatorImpl(fruitService);
    }

    @Test
    public void createReport_Ok() {
        Storage.fruitsStorage.put("orange", 40);
        Storage.fruitsStorage.put("apple", 67);
        String actual = reportCreator.makeReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,balance")
                .append(System.lineSeparator())
                .append("orange,40")
                .append(System.lineSeparator())
                .append("apple,67")
                .append('\n');
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        String actual = reportCreator.makeReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,balance").append('\n');
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
