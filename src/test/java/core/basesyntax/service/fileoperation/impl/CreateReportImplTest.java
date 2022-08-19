package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.fileoperation.CreateReport;
import org.junit.After;
import org.junit.Test;

public class CreateReportImplTest {
    private static final String TITLE = "fruit,quantity"
            + System.lineSeparator();
    private static final StringBuilder builder = new StringBuilder();
    private static final StorageDao dao = new StorageDaoImpl();
    private static final CreateReport report = new CreateReportImpl(builder, dao);

    @After
    public void clear_storage() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void get_report_Ok() {
        dao.addFruit(new Fruit("apple", 40));
        dao.addFruit(new Fruit("peach", 50));
        dao.addFruit(new Fruit("apricot", 10));
        String expected = TITLE
                + "apple,40" + System.lineSeparator()
                + "peach,50" + System.lineSeparator()
                + "apricot,10" + System.lineSeparator();
        String actual = report.getReport();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_reportNull_notOk() {
        report.getReport();
    }
}
