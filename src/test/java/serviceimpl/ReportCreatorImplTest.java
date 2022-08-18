package serviceimpl;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.ReportCreator;

public class ReportCreatorImplTest {
    private static String HEADER = "fruits, quantity";
    private static ReportCreator reportCreator;
    private static Storage storage;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @Test
    public void reportCreator_Ok() {
        FruitTransaction banana = new FruitTransaction();
        banana.setFruit("banana");
        banana.setOperation(FruitTransaction.Operation.BALANCE);
        banana.setQuantity(152);
        FruitTransaction apple = new FruitTransaction();
        apple.setFruit("apple");
        apple.setOperation(FruitTransaction.Operation.BALANCE);
        apple.setQuantity(90);
        storageDao.update("banana", 152);
        storageDao.update("apple", 90);
        String expected = HEADER + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportCreator.create();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportCreator_Not_OK() {
        reportCreator = new ReportCreatorImpl(null);
        reportCreator.create();
    }

    @Test
    public void reportEmpty_NotOk() {
        String expected = "";
        String actual = reportCreator.create();
        Assert.assertNotEquals(expected,actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
