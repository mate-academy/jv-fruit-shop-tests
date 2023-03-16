package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.UnaryOperation;
import core.basesyntax.strategy.impl.ApplySupplay;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApplySupplayTest {
    private static final String FRUIT_OK = "apple";
    private static final Integer AMOUNT_OK = 100;
    private static UnaryOperation unaryOperation;

    @BeforeClass
    public static void beforeClass() {
        unaryOperation = new ApplySupplay();
    }

    @Test(expected = FruitStoreException.class)
    public void apply_null_NotOk() {
        unaryOperation.apply(null);
    }

    @Test
    public void apply_Ok() {
        Storage.storage.clear();
        FruitTransaction fruit = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                FRUIT_OK,
                AMOUNT_OK);
        unaryOperation.apply(fruit);
        Integer expected = AMOUNT_OK;
        Integer actual = Storage.storage.get(FRUIT_OK);
        assertEquals(actual, expected);
    }
}
