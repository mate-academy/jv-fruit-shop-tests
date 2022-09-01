package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitMovement;
import core.basesyntax.model.MovementType;
import core.basesyntax.storage.impl.FruitDaoImpl;
import core.basesyntax.strategy.GoodHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseMovementHandlerTest {
    private static GoodHandler handler;
    private static FruitDaoImpl storage;
    private FruitMovement request;

    @BeforeClass
    public static void setUp() {
        storage = new FruitDaoImpl();
        handler = new PurchaseMovementHandler(storage);
    }

    @Test(expected = NullPointerException.class)
    public void makePosting_NullValue_NotOk() {
        handler.makePosting(null);
    }

    @Test
    public void makePosting_OneFruitMovements_Ok() {
        Fruit fruit = new Fruit("fruit");
        request = new FruitMovement(fruit, MovementType.PURCHASE, 10);
        storage.updateBalance(fruit, 0);
        handler.makePosting(request);
        assertTrue(storage.getAmount(fruit) == -10);

        request = new FruitMovement(fruit, MovementType.PURCHASE, 50);
        handler.makePosting(request);
        assertTrue(storage.getEntries().size() == 1);
        assertTrue(storage.getAmount(fruit) == -60);
    }

    @Test
    public void makePosting_manyFruitsMovements_Ok() {
        int numberOfFruits = 100;
        for (int i = 0; i < numberOfFruits; i++) {
            Fruit fruit = new Fruit(String.valueOf(i));
            request = new FruitMovement(fruit, MovementType.PURCHASE, 10);
            storage.updateBalance(fruit, 0);
            handler.makePosting(request);
        }
        assertTrue(storage.getEntries().size() == numberOfFruits);

        for (int i = 0; i < numberOfFruits; i++) {
            request = new FruitMovement(new Fruit(String.valueOf(i)), MovementType.PURCHASE, 100);
            handler.makePosting(request);
        }
        assertTrue(storage.getEntries().size() == numberOfFruits);
    }

    @After
    public void storageClear() {
        storage.clearStorage();
    }
}
