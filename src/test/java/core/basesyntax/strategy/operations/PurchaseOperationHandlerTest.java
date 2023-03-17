package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.PURCHASE;
    private static final String NAME_OF_FRUIT = "apple";
    private static final int QUANTITY_OF_FRUIT = 20;
    private static final int NOT_ENOUGH_QUANTITY_OF_FRUIT = 10;
    private PurchaseOperationHandler purchaseOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OPERATION);
        fruitTransaction.setFruit(NAME_OF_FRUIT);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
    }

    @Test
    public void handleOperation_defaultCase_ok() {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        Integer expected = Storage.fruits.get(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        purchaseOperationHandler.handleOperation(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void handleOperation_fruitTransactionIsNull_notOk() {
        purchaseOperationHandler.handleOperation(null);
        fail("You must throw Runtime Exception, if the passed Fruit Transaction is null");
    }

    @Test (expected = RuntimeException.class)
    public void handleOperation_NotEnoughFruitsInTheStorage_notOk() {
        Storage.fruits.put(NAME_OF_FRUIT,NOT_ENOUGH_QUANTITY_OF_FRUIT);
        purchaseOperationHandler.handleOperation(fruitTransaction);
        fail("You must throw Runtime Exception, if it is not enough fruits in the storage");
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
