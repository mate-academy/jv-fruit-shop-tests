package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operation.FruitSupplyOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitSupplyOperationTest {
    private static final String FRUIT_NAME = "durian";
    private static final int INVALID_FRUIT_QUANTITY = -1;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int FRUIT_QUANTITY = 100;
    private static final int FRUIT_IN_STORAGE_QUANTITY = 110;
    private FruitTransaction transaction;

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
        FruitOperation operation = new FruitSupplyOperation();
        Storage.fruitStorage.put(FRUIT_NAME, FRUIT_IN_STORAGE_QUANTITY);
        operation.fruitOperate(transaction);
        Assert.assertEquals(Integer.valueOf(FRUIT_IN_STORAGE_QUANTITY + FRUIT_QUANTITY),
                Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_invalidInputData() {
        FruitOperation operation = new FruitSupplyOperation();
        transaction.setQuantity(INVALID_FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_quantityIsZero_notOk() {
        FruitOperation operation = new FruitSupplyOperation();
        transaction.setQuantity(ZERO_FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
    }
}
