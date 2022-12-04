package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static String FRUIT = "apple";
    private OperationHandler supplyHandler;

    @Before
    public void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void addSupplyHandler_existProduct_ok() {
        Storage.fruits.put(FRUIT, 20);
        FruitTransaction supply = new FruitTransaction(Operation.getByCode("s"), FRUIT, 20);
        supplyHandler.operate(supply);
        int expected = 40;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void addSupplyHandler_newProduct_ok() {
        FruitTransaction supply = new FruitTransaction(Operation.getByCode("s"), FRUIT, 20);
        supplyHandler.operate(supply);
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void addSupplyHandler_negativeQty_ok() {
        Storage.fruits.put(FRUIT, 20);
        FruitTransaction supply = new FruitTransaction(Operation.getByCode("s"), FRUIT, -20);
        supplyHandler.operate(supply);
        int expected = 40;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
