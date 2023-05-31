package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Operation SUPPLY = Operation.SUPPLY;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void handle_validTransactionQuantityNonExistFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setName("orange");
        fruitTransaction.setQuantity(5);
        fruitTransaction.setOperation(SUPPLY);
        operationHandler.handle(fruitTransaction);
        Integer expectedQuantity = 5;
        Integer actualQuantity = Storage.fruits.get("orange");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                + actualQuantity, expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_validTransactionQuantity_Ok() {
        Storage.fruits.put("orange", 5);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setName("orange");
        fruitTransaction.setQuantity(6);
        fruitTransaction.setOperation(SUPPLY);
        operationHandler.handle(fruitTransaction);
        Integer expectedQuantity = 11;
        Integer actualQuantity = Storage.fruits.get("orange");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
