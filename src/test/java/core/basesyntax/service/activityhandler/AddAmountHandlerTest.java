package core.basesyntax.service.activityhandler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoMapImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.AddAmountHandler;
import core.basesyntax.service.handler.RecordHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddAmountHandlerTest {
    private static FruitDao fruitDao;
    private static RecordHandler addAmountHandler;

    @BeforeClass
    public static void setFruitDao() {
        fruitDao = new FruitDaoMapImpl();
        addAmountHandler = new AddAmountHandler(fruitDao);
    }

    @Before
    public void cleanMapDB() {
        Storage.storage.clear();
    }

    @Test
    public void changeBalance_addBalanceWhenFruitExistInDb_amountIncrease() {
        fruitDao.save(new Fruit("apple", 50));
        Fruit inputFruit = new Fruit("apple", 25);
        long changedBalance = addAmountHandler.changeBalance(inputFruit);
        assertEquals(75, changedBalance);
    }

    @Test
    public void changeBalance_addBalanceWhenFruitNotExistInDb_sizeTheSame() {
        Fruit inputFruit = new Fruit("apple", 25);
        long changedBalance = addAmountHandler.changeBalance(inputFruit);
        assertEquals(25, changedBalance);
    }
}
