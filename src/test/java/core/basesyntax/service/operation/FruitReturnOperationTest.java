package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReturnOperationTest {
    private static final String FRUIT_NAME = "durian";
    private static final int INVALID_FRUIT_QUANTITY = -1;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int FRUIT_QUANTITY = 100;
    private static final int FRUIT_IN_STORAGE_QUANTITY = 110;
    private static FruitOperation operation;
    private FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operation = new FruitReturnOperation();
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
        Storage.fruitStorage.put(FRUIT_NAME, FRUIT_IN_STORAGE_QUANTITY);
        operation.fruitOperate(transaction);
        Assert.assertEquals(Integer.valueOf(FRUIT_IN_STORAGE_QUANTITY + FRUIT_QUANTITY),
                Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_quantityIsLessThanZero_notOk() {
        transaction.setQuantity(INVALID_FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_quantityIsZero_notOk() {
        transaction.setQuantity(ZERO_FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
    }
}
