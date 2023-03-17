package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private final OperationTypeHandler supplyHandler = new SupplyHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void supply_nullValue_notOk() {
        Integer nullValue = null;
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(nullValue);
        supplyHandler.handle(fruitTransaction);
    }

    @Test
    public void supply_zeroValue_ok() {
        Storage.storage.put("banana", 100);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(0);
        Integer expected = Storage.storage.get(fruitTransaction.getFruit());
        supplyHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void supply_negativeValue_notOk() {
        Storage.storage.put("negative",0);
        fruitTransaction.setFruit("negative");
        fruitTransaction.setQuantity(-10);
        supplyHandler.handle(fruitTransaction);
    }

    @Test
    public void supply_validValue_ok() {
        Storage.storage.put("valid", 99);
        fruitTransaction.setFruit("valid");
        fruitTransaction.setQuantity(1);
        Integer expected = 100;
        supplyHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("valid");
        assertEquals(actual, expected);
    }
}
