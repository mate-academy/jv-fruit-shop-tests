package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static BuyOperationHandler buyOperation;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        transaction = creteTransaction(FruitTransaction.Operation.PURCHASE, FRUIT, EXPECTED);
        buyOperation = new BuyOperationHandler();
    }

    @Test
    public void purchaseOperation_handle_Ok() {
        Storage.fruits.put("banana", 40);
        buyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notEnoughInStorage_NotOk() {
        buyOperation.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notFoundInStorage_NotOk() {
        buyOperation.handle(null);
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
