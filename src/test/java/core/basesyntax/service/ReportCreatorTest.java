package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.StorageException;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReportCreatorTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getReport_properData_ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String[] actual = reportCreator.getReport();
        String[] expected = new String[] {"fruit,quantity", "banana,152", "apple,90"};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void getReport_emptyStorage_notOk() {
        Assertions.assertThrows(StorageException.class,
                () -> reportCreator.getReport());
    }
}
