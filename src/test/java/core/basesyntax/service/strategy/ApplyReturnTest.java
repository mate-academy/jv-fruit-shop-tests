package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.UnaryOperation;
import core.basesyntax.strategy.impl.ApplyReturn;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApplyReturnTest {
    private static final Integer AMOUNT_OK = 100;
    private static final String FRUIT_OK = "apple";
    private static UnaryOperation unaryOperation;

    @BeforeClass
    public static void beforeClass() {
        unaryOperation = new ApplyReturn();
    }

    @Test(expected = FruitStoreException.class)
    public void apply_null_NotOk() {
        unaryOperation.apply(null);
        fail("Expected " + FruitStoreException.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test
    public void apply_Ok() {
        Storage.storage.clear();
        FruitTransaction fruit = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,"apple", 100);
        unaryOperation.apply(fruit);
        Integer expected = AMOUNT_OK;
        Integer actual = Storage.storage.get(FRUIT_OK);
        assertEquals("Expected amount of fruit = " + expected + ", but was " + actual,
                actual, expected);
    }
}
