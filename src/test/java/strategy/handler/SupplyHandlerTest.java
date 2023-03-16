package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class SupplyHandlerTest {
    private static TransactionHandler supplyHandler;
    private static FruitTransaction supply;

    @Before
    public void setUp() {
        supplyHandler = new SupplyHandler();

        supply = new FruitTransaction();
        supply.setOperation(FruitTransaction.Operation.SUPPLY);
        supply.setFruit("banana");
        supply.setQuantity(10);

        Storage.fruits.put("banana", 10);
    }

    @Test
    public void handle_SupplyTransaction_Ok() {
        Integer expected = 20;
        supplyHandler.handle(supply);
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
