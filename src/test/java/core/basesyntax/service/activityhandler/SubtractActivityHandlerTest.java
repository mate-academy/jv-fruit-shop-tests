package core.basesyntax.service.activityhandler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoMapImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.RecordHandler;
import core.basesyntax.service.handler.SubtractAmountHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractActivityHandlerTest {

    private static FruitDao fruitDao;
    private static RecordHandler subtractHandler;

    @BeforeClass
    public static void setFruitDao() {
        fruitDao = new FruitDaoMapImpl();
        subtractHandler = new SubtractAmountHandler(fruitDao);
    }

    @Before
    public void cleanMapDB() {
        Storage.storage.clear();
    }

    @Test
    public void changeBalance_buyFruitExistInDbAndBalanceIsEnough_balanceIsReduced() {
        fruitDao.save(new Fruit("apple", 50));
        Fruit inputFruit = new Fruit("apple", 25);
        long changedBalance = subtractHandler.changeBalance(inputFruit);
        assertEquals(25, changedBalance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBalance_buyFruitExistInDbButBalanceIsNotEnough_IllegalArgumentException() {
        fruitDao.save(new Fruit("apple", 50));
        Fruit inputFruit = new Fruit("apple", 75);
        subtractHandler.changeBalance(inputFruit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBalance_buyFruitNotExistInDb_IllegalArgumentException() {
        Fruit inputFruit = new Fruit("apple", 75);
        subtractHandler.changeBalance(inputFruit);
    }
}
