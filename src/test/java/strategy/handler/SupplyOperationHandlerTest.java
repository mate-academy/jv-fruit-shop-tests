package strategy.handler;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY = 100;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler(new FruitDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void supply_banana_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_BANANA, QUANTITY - 10);
        Storage.fruits.put(FRUIT_BANANA, QUANTITY);
        supplyOperationHandler.add(fruit);
        assertEquals((Integer) 190, Storage.fruits.get(FRUIT_BANANA));
    }
}
