package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ResultGeneratorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResultGeneratorServiceImplTest {
    private static FruitStorageDao fruitStorageDao;
    private static ResultGeneratorService generator;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        generator = new ResultGeneratorServiceImpl(fruitStorageDao);
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.add(new Fruit("banana"), 152);
        fruitStorageDao.add(new Fruit("apple"), 90);
    }

    @Test
    public void generateResult_validResult_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = generator.generateResult();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateResult_emptyLine_ok() {
        fruitStorageDao.getAll().clear();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = generator.generateResult();
        Assert.assertEquals(expected, actual);
    }
}
