package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceTest {
    private static StorageDao<Fruit> storageDao;
    private static StorageService storageService;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategy(storageDao);
        storageService = new StorageServiceImpl(storageDao, operationStrategy);
    }

    @Before
    public void setupStorage() {
        storageDao.add(new Fruit("banana", 100));
        storageDao.add(new Fruit("apple", 50));
    }

    @Test
    public void updateStorage_Ok() {
        List<String[]> input = List.of(new String[]{"b", "banana", "100"},
                new String[]{"b", "apple", "50"},
                new String[]{"s", "banana", "100"},
                new String[]{"p", "apple", "10"});
        storageService.updateStorage(input);
        List<Fruit> expected = List.of(new Fruit("banana", 200),
                new Fruit("apple", 40));
        List<Fruit> actual = storageDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStatistic_Ok() {
        List<String> expected = List.of("banana,100", "apple,50");
        List<String> actual = storageService.getStorageStatistic();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void resetStorage() {
        Storage.fruitStorage.clear();
    }
}
