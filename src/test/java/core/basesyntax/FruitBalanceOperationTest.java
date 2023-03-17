package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operation.FruitBalanceOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitBalanceOperationTest {
    private static final String FRUIT_NAME = "durian";
    private static final int INVALID_FRUIT_QUANTITY = -1;
    private static final int FRUIT_QUANTITY = 100;
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
        FruitOperation operation = new FruitBalanceOperation();
        operation.fruitOperate(transaction);
        Assert.assertEquals(Integer.valueOf(FRUIT_QUANTITY),
                Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void fruitOperate_quantityIsLessThanZero_notOk() {
        FruitOperation operation = new FruitBalanceOperation();
        transaction.setQuantity(INVALID_FRUIT_QUANTITY);
        operation.fruitOperate(transaction);
    }
}
