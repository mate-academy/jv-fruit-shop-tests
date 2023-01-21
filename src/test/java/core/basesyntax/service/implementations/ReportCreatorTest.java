package core.basesyntax.service.implementations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    private static final String REPORT_TEMPLATE = "fruit,quantity";
    private StorageDao storageDao;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreator();
        storageDao.add("banana", 23);
        storageDao.add("apple", 10);
    }

    @Test
    public void provideReport_ok() {
        String expectedString = REPORT_TEMPLATE + System.lineSeparator() + "banana,23"
                + System.lineSeparator() + "apple,10";
        String actualString = reportCreator.provideReport(storageDao.getAll());
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void provideReport_emptyStorage_ok() {
        String actualString = reportCreator.provideReport(new HashMap<>());
        Assertions.assertEquals(REPORT_TEMPLATE, actualString);
    }

    @Test
    public void provideReport_nullStorage_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> reportCreator.provideReport(null));
    }
}
