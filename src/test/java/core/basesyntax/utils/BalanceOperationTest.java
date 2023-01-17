package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.BalanceOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static CalculateOperation operation;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operation = new BalanceOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void operations_balanceOperationWithValidData_ok() {
        final Integer expected = 10;
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("grape");
        fruitTransaction.setQuantity(10);
        operation.count(fruitTransaction);
        Integer actual = StorageOfData.fruitsData.get("grape");
        assertEquals(expected, actual);
    }
}
