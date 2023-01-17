package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.ReturnOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static CalculateOperation operation;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operation = new ReturnOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void operations_returnOperationWithValidData_ok() {
        final Integer expected = 15;
        StorageOfData.fruitsData.put("grape", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("grape");
        fruitTransaction.setQuantity(5);
        operation.count(fruitTransaction);
        Integer actual = StorageOfData.fruitsData.get("grape");
        assertEquals(expected, actual);
    }
}
