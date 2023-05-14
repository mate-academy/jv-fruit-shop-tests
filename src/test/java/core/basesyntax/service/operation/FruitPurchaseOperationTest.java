package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitPurchaseOperationTest {
    private static final String FRUIT_NAME = "durian";
    private static final int EMPTY_STORAGE = 0;
    private static final int FRUIT_QUANTITY = 100;
    private static final int FRUIT_START_BALANCE = 110;
    private static FruitOperation operation;
    private FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operation = new FruitPurchaseOperation();
    }

    @Before
    public void setUpTransaction() {
        transaction = new FruitTransaction();
        transaction.setFruit(FRUIT_NAME);
        transaction.setQuantity(FRUIT_QUANTITY);
    }

    @After
    public void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void fruitOperate_validInputData_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, FRUIT_START_BALANCE);
        operation.fruitOperate(transaction);
        int actualQuantity = Storage.fruitStorage.get(FRUIT_NAME);
        Assert.assertEquals(FRUIT_START_BALANCE - FRUIT_QUANTITY, actualQuantity);
    }

    @Test
    public void fruitOperate_fruitBalanceIsEqualsToPurchaseQuantity_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
        int actualQuantity = Storage.fruitStorage.get(FRUIT_NAME);
        Assert.assertEquals(EMPTY_STORAGE, actualQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_fruitQuantityIsHigherThanBalance_notOk() {
        operation.fruitOperate(transaction);
    }
}
