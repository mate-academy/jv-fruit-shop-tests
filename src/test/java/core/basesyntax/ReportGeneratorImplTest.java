package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReportGeneratorImplTest {
    @Test
    public void generateReport_getReport_0k() {
        StorageDao storageDao = new StorageDaoImpl();
        Fruit fruit = new Fruit("apple");
        storageDao.add(fruit, 50);
        String expected = "apple,50";
        List<String> list = new ReportGeneratorImpl().generateReport(storageDao);
        Assert.assertEquals(storageDao.size(), list.size());
        Assert.assertEquals(expected, list.get(0));
    }
}
