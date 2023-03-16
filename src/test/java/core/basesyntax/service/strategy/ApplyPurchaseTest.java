package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.UnaryOperation;
import core.basesyntax.strategy.impl.ApplyPurchase;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApplyPurchaseTest {
    private static final String FRUIT_OK = "apple";
    private static final Integer NEGATIVE_AMOUNT = -100;
    private static final Integer AMOUNT_OK = 100;
    private static UnaryOperation unaryOperation;

    @BeforeClass
    public static void beforeClass() {
        unaryOperation = new ApplyPurchase();
    }

    @Test(expected = FruitStoreException.class)
    public void apply_nullFruit_NotOk() {
        unaryOperation.apply(null);
    }

    @Test(expected = FruitStoreException.class)
    public void apply_not_enoughAmount_NotOk() {
        Storage.storage.clear();
        FruitTransaction fruit = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                FRUIT_OK,
                AMOUNT_OK);
        unaryOperation.apply(fruit);
    }

    @Test(expected = FruitStoreException.class)
    public void apply_negativeAmount_NotOk() {
        FruitTransaction fruit = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                FRUIT_OK,
                NEGATIVE_AMOUNT);
        unaryOperation.apply(fruit);
    }
}
