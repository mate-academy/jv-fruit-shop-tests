package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final String NAME_OF_FRUIT = "banana";
    private static final int QUANTITY_OF_FRUIT = 20;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OPERATION);
        fruitTransaction.setFruit(NAME_OF_FRUIT);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
    }

    @Test
    public void handleOperation_defaultCase_ok() {
        Storage.fruits.put(NAME_OF_FRUIT, QUANTITY_OF_FRUIT);
        Integer expected = Storage.fruits.get(fruitTransaction.getFruit());
        balanceOperationHandler.handleOperation(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void handleOperation_fruitTransactionIsNull_notOk() {
        balanceOperationHandler.handleOperation(null);
        fail("You must throw Runtime Exception, if the fruit transaction is null");
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
