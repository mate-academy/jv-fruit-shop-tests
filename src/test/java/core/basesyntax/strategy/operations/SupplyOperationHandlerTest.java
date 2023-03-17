package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.SUPPLY;
    private static final String NAME_OF_FRUIT = "banana";
    private static final int QUANTITY_OF_FRUIT = 100;
    private SupplyOperationHandler supplyOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OPERATION);
        fruitTransaction.setFruit(NAME_OF_FRUIT);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
    }

    @Test
    public void handleOperation_defaultCase_ok() {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        Integer expected = Storage.fruits.get(fruitTransaction.getFruit())
                + fruitTransaction.getQuantity();
        supplyOperationHandler.handleOperation(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void handleOperation_fruitTransactionIsNull_notOk() {
        supplyOperationHandler.handleOperation(null);
        fail("You must throw Runtime Exception, if the passed Fruit Transaction is null");
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
