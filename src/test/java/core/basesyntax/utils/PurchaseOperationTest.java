package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.PurchaseOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {

    private static CalculateOperation operation;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operation = new PurchaseOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void operations_purchaseOperationWithValidData_ok() {
        final Integer expected = 5;
        StorageOfData.fruitsData.put("grape", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("grape");
        fruitTransaction.setQuantity(5);
        operation.count(fruitTransaction);
        Integer actual = StorageOfData.fruitsData.get("grape");
        assertEquals(expected, actual);
    }
}
