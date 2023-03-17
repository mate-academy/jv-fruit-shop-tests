package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static SupplyOperationHandler supplyOperation;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        transaction = creteTransaction(FruitTransaction.Operation.SUPPLY, FRUIT, EXPECTED);
        supplyOperation = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperation_handle_Ok() {
        supplyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_notFoundInStorage_NotOk() {
        supplyOperation.handle(null);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }

    private static FruitTransaction creteTransaction(FruitTransaction.Operation operation,
                                                     String fruitName,
                                                     int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
