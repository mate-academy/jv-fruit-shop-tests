package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorTest {
    private static final String TITLE = "fruit,quantity";
    private static StorageDao storageDao;

    private static ReportCreatorImpl reportCreator;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @Test
    public void newReport_reportCreate_Ok() {
        storageDao.update("banana", 25);
        storageDao.update("apple", 30);
        String expected = TITLE + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,30";
        String actual = reportCreator.create();
        assertEquals(expected, actual);
    }
}
