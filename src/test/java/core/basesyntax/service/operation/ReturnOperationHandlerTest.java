package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        FruitStorage.fruitStorage.put("banana", 20);
    }

    @Test
    public void handle_returnOperationNormalQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(12);
        returnOperationHandler.handle(fruitTransaction);
        int expected = 32;
        int actual = FruitStorage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
