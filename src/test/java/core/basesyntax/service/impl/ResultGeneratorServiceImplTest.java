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
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.getAll().clear();
        fruitStorageDao.add(new Fruit("banana"), 152);
        fruitStorageDao.add(new Fruit("apple"), 90);
        generator = new ResultGeneratorServiceImpl(fruitStorageDao);
    }

    @Test
    public void resultGenerate_validResult_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = generator.generateResult();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void resultGenerate_emptyLine_Ok() {
        fruitStorageDao.getAll().clear();
        generator = new ResultGeneratorServiceImpl(fruitStorageDao);
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = generator.generateResult();
        Assert.assertEquals(expected, actual);
    }
}
