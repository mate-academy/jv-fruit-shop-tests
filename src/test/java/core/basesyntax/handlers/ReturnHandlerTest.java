package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private final OperationTypeHandler returnHandler = new ReturnHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void return_nullValue_notOk() {
        Storage.storage.put("orange", 33);
        Integer nullValue = null;
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(nullValue);
        returnHandler.handle(fruitTransaction);
    }

    @Test
    public void return_zeroValue_ok() {
        Storage.storage.put("banana", 100);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(0);
        Integer expected = Storage.storage.get(fruitTransaction.getFruit());
        returnHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void return_negativeValue_notOk() {
        Storage.storage.put("negative", 20);
        fruitTransaction.setFruit("negative");
        fruitTransaction.setQuantity(-10);
        returnHandler.handle(fruitTransaction);
    }

    @Test
    public void return_validValue_ok() {
        Storage.storage.put("valid", 99);
        fruitTransaction.setFruit("valid");
        fruitTransaction.setQuantity(1);
        Integer expected = 100;
        returnHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("valid");
        assertEquals(actual, expected);
    }
}
