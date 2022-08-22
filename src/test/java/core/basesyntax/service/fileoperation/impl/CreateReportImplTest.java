package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.fileoperation.CreateReport;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportImplTest {
    private static final String TITLE = "fruit,quantity"
            + System.lineSeparator();
    private static CreateReport report;

    @After
    public void clear_storage() {
        FruitShopStorage.storageFruits.clear();
    }

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        report = new CreateReportImpl(storageDao);
    }

    @Test
    public void getReport_validReport_Ok() {
        FruitShopStorage.storageFruits.add(new Fruit("apple", 40));
        FruitShopStorage.storageFruits.add(new Fruit("peach", 50));
        FruitShopStorage.storageFruits.add(new Fruit("apricot", 10));
        String expected = TITLE
                + "apple,40" + System.lineSeparator()
                + "peach,50" + System.lineSeparator()
                + "apricot,10" + System.lineSeparator();
        String actual = report.getReport();
        assertEquals(expected, actual);
    }
}
