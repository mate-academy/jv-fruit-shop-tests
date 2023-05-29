package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.SupplyOperation;
import org.junit.Test;

public class SupplyOperationTest {

    @Test
    public void operations_returnOperationWithValidData_ok() {
        final CalculateOperation operation = new SupplyOperation();
        FruitTransaction fruitTransaction = new FruitTransaction();
        final Integer expected = 15;
        StorageOfData.fruitsData.put("grape", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("grape");
        fruitTransaction.setQuantity(5);
        operation.count(fruitTransaction);
        Integer actual = StorageOfData.fruitsData.get("grape");
        assertEquals(expected, actual);
    }
}
