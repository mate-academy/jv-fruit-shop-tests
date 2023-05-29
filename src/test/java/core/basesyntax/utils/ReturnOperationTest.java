package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.ReturnOperation;
import org.junit.Test;

public class ReturnOperationTest {

    @Test
    public void operations_returnOperationWithValidData_ok() {
        final CalculateOperation operation = new ReturnOperation();
        FruitTransaction fruitTransaction = new FruitTransaction();
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
