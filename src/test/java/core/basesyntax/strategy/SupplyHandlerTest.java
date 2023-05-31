package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final String FRUIT = "banana";
    private OperationHandler supplyHandler;

    @Before
    public void setUp() {
        supplyHandler = new SupplyOperationStrategy();
    }

    @Test
    public void handle_supplyHandle_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(40);
        supplyHandler.handle(transaction);
        Integer expected = 40;
        Integer actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
